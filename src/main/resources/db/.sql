DROP TABLE IF EXISTS person, card, card_account, passport, card_transaction, account_transaction;
DROP TYPE currency_type;

CREATE TABLE if not exists passport
(
    id            BIGSERIAL PRIMARY KEY,
    dob           TIMESTAMP    NOT NULL,
    identity_code VARCHAR(14)  NOT NULL,
    name          VARCHAR(255) NOT NULL,
    surname       VARCHAR(255) NOT NULL,
    succession    VARCHAR(255)
);

CREATE TABLE if not exists person
(
    id          BIGSERIAL PRIMARY KEY,
    email       varchar(50) CHECK (person.email ~* '^\S+@\S+\.\S+$')
                                                NOT NULL UNIQUE,
    password    VARCHAR(72)                     NOT NULL,
    passport_id BIGINT REFERENCES passport (id)
);

CREATE TABLE if not exists card_account
(
    id            BIGSERIAL PRIMARY KEY,
    money         DECIMAL     NOT NULL,
    iban          VARCHAR(28) NOT NULL,
    number        VARCHAR(16) NOT NULL,
    currency_type VARCHAR(10) NOT NULL,
    person_id     BIGINT REFERENCES person (id) NOT NULL
);

CREATE TABLE if not exists card
(
    id              BIGSERIAL PRIMARY KEY,
    pin             VARCHAR(4)                          NOT NULL,
    number          VARCHAR(16)                         NOT NULL,
    cvv             VARCHAR(3)                          NOT NULL,
    expiration_time TIMESTAMP CHECK (EXTRACT(YEAR FROM expiration_time)::int >
                                     EXTRACT(YEAR FROM current_timestamp)::int),
    person_id       BIGINT REFERENCES person (id)       NOT NULL,
    account_id      BIGINT REFERENCES card_account (id) NOT NULL
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
    FOREIGN KEY (from_account_id) REFERENCES card_account (id),
    FOREIGN KEY (to_account_id) REFERENCES card_account (id)
);

CREATE TABLE IF NOT EXISTS card_transaction
(
    id           UUID      DEFAULT md5(random()::text || clock_timestamp()::text)::uuid NOT NULL UNIQUE,
    from_card_id BIGINT                                                                 NOT NULL,
    to_card_id   BIGINT                                                                 NOT NULL,
    amount       NUMERIC(10, 3)                                                         NOT NULL,
    currency     currency_type                                                          NOT NULL,
    being_at     TIMESTAMP DEFAULT now(),
    FOREIGN KEY (from_card_id) REFERENCES card (id),
    FOREIGN KEY (to_card_id) REFERENCES card (id)
);