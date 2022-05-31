DROP TABLE IF EXISTS users, cards, card_accounts, holdings, credits, passports, sns, transactions, users;

CREATE TABLE if not exists sns
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    surname    VARCHAR(255) NOT NULL,
    succession VARCHAR(255) NOT NULL
);
CREATE TABLE if not exists passports
(
    id            BIGSERIAL PRIMARY KEY,
    dob           TIMESTAMP                  NOT NULL,
    identity_code VARCHAR(14)                NOT NULL,
    sns_id        BIGINT REFERENCES sns (id) NOT NULL
);

CREATE TABLE if not exists users
(
    id          BIGSERIAL PRIMARY KEY,
    email       varchar(50) CHECK (users.email ~ '/^\\S+@\\S+\\.\\S+$/')
                                                 NOT NULL UNIQUE,
    password    VARCHAR(72)                      NOT NULL,
    passport_id BIGINT REFERENCES passports (id) NOT NULL
);

CREATE TABLE if not exists card_accounts
(
    id            BIGSERIAL PRIMARY KEY,
    money         DECIMAL     NOT NULL,
    iban          VARCHAR(28) NOT NULL,
    number        VARCHAR(15) NOT NULL,
    currency_type VARCHAR(10) NOT NULL
);

CREATE TABLE if not exists cards
(
    id              BIGSERIAL PRIMARY KEY,
    pin             VARCHAR(4)                           NOT NULL,
    number          VARCHAR(16)                          NOT NULL,
    cvv             VARCHAR(3)                           NOT NULL,
    expiration_time TIMESTAMP CHECK (EXTRACT(YEAR FROM expiration_time)::int >
                                     EXTRACT(YEAR FROM current_timestamp)::int),
    user_id         BIGINT REFERENCES users (id)         NOT NULL,
    account_id      BIGINT REFERENCES card_accounts (id) NOT NULL
);

CREATE TABLE if not exists holdings
(
    id            BIGSERIAL PRIMARY KEY,
    issuance_date TIMESTAMP  NOT NULL,
    amount        DECIMAL    NOT NULL,
    percentage    DECIMAL    NOT NULL,
    currency_type VARCHAR(4) NOT NULL,
    passport_id   BIGINT REFERENCES passports (id)
);

CREATE TABLE if not exists credits
(
    id            smallint PRIMARY KEY,
    issuance_date TIMESTAMP  NOT NULL,
    amount        DECIMAL    NOT NULL,
    currency_type VARCHAR(4) NOT NULL,
    passport_id   BIGINT     NOT NULL UNIQUE,
    FOREIGN KEY (passport_id) REFERENCES passports (id)
);

CREATE TYPE currency_type AS ENUM (
    'EUR',
    'USD',
    'RUB',
    'BYN'
);

CREATE TABLE IF NOT EXISTS account_transaction
(
    id              UUID      DEFAULT md5(random()::text || clock_timestamp()::text)::uuid NOT NULL UNIQUE,
    from_account_id BIGINT                                                                 NOT NULL,
    to_account_id   BIGINT                                                                 NOT NULL,
    amount          NUMERIC(10, 3)                                                         NOT NULL,
    currency        currency_type                                                          NOT NULL,
    being_at        TIMESTAMP DEFAULT now(),
    FOREIGN KEY (from_account_id) REFERENCES card_accounts (id),
    FOREIGN KEY (to_account_id) REFERENCES card_accounts (id)
);

CREATE TABLE IF NOT EXISTS card_transaction
(
    id           UUID      DEFAULT md5(random()::text || clock_timestamp()::text)::uuid NOT NULL UNIQUE,
    from_card_id BIGINT                                                                 NOT NULL,
    to_card_id   BIGINT                                                                 NOT NULL,
    amount       NUMERIC(10, 3)                                                         NOT NULL,
    currency     currency_type                                                          NOT NULL,
    being_at     TIMESTAMP DEFAULT now(),
    FOREIGN KEY (from_card_id) REFERENCES cards (id),
    FOREIGN KEY (to_card_id) REFERENCES cards (id)
);
DROP FUNCTION IF EXISTS made_account_transaction(to_account_id bigint, from_account_id bigint, amount numeric(10, 3), currencies varchar);

CREATE FUNCTION
    made_account_transaction(to_account_id bigint, from_account_id bigint, amount numeric(10, 3), currencies varchar)
    RETURNS boolean AS $$
DECLARE
    from_account_currency_type currency_type;
    to_account_currency_type currency_type;
    from_account_currency numeric(10, 3);
    to_account_currency numeric(10, 3);
    money_var numeric(10, 3);
    temp_amount numeric(10, 3);
    USD constant text := 'USD';
BEGIN
    SELECT money INTO money_var FROM card_accounts WHERE id = from_account_id;

    IF money_var < amount OR amount < 0 THEN
        RAISE EXCEPTION 'Not enough money to make a transaction in card' USING ERRCODE = 'P0001';
    ELSEIF to_account_id = from_account_id THEN
        RAISE EXCEPTION 'Reflection transaction is not allowed' USING ERRCODE = 'P0001';
    END IF;

    SELECT currency_type INTO from_account_currency_type FROM card_accounts WHERE id = from_account_id;
    SELECT currency_type INTO to_account_currency_type FROM card_accounts WHERE id = to_account_id;

    IF from_account_currency_type = to_account_currency_type THEN
        UPDATE card_accounts SET money = @money + amount WHERE id = to_account_id;
    ELSE
        IF from_account_currency_type::text NOT LIKE USD THEN

            SELECT value INTO from_account_currency
            FROM (SELECT * FROM json_each(currencies::json)) AS "*2"
            WHERE key LIKE concat('%', from_account_currency_type::text);

            temp_amount := amount / from_account_currency;
        ELSE
            temp_amount := amount;
        END IF;
        IF to_account_currency_type::text != USD THEN
            SELECT value INTO to_account_currency
            FROM (SELECT * FROM json_each(currencies::json)) AS "*2"
            WHERE key LIKE concat('%', to_account_currency_type::text);
            raise notice '%', to_account_currency;

            temp_amount := temp_amount * to_account_currency;
            raise notice '%', temp_amount;
        END IF;
        UPDATE card_accounts SET money = @money + temp_amount WHERE id = to_account_id;
    END IF;

    UPDATE card_accounts SET money = @money - amount WHERE id = from_account_id;

    RETURN true;
END;
$$ LANGUAGE plpgsql
    SECURITY DEFINER;