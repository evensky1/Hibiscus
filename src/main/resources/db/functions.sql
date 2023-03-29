DROP FUNCTION IF EXISTS made_account_transaction(to_account_id bigint, from_account_id bigint,
                                                 amount numeric(10, 3), currencies varchar);

CREATE FUNCTION
    made_account_transaction(to_account_id bigint, from_account_id bigint, amount numeric(10, 3),
                             currencies varchar)
    RETURNS boolean AS
$$
DECLARE
    from_account_currency_type currency_type;
    to_account_currency_type   currency_type;
    from_account_currency      numeric(10, 3);
    to_account_currency        numeric(10, 3);
    money_var                  numeric(10, 3);
    temp_amount                numeric(10, 3);
    USD constant               text := 'USD';
BEGIN
    SELECT money INTO money_var FROM card_account WHERE id = from_account_id;

    IF money_var < amount OR amount < 0 THEN
        RAISE EXCEPTION 'Not enough money to make a transaction in card' USING ERRCODE = 'P0001';
    ELSEIF to_account_id = from_account_id THEN
        RAISE EXCEPTION 'Reflection transaction is not allowed' USING ERRCODE = 'P0001';
    END IF;

    SELECT currency_type
    INTO from_account_currency_type
    FROM card_account
    WHERE id = from_account_id;
    SELECT currency_type INTO to_account_currency_type FROM card_account WHERE id = to_account_id;

    IF from_account_currency_type = to_account_currency_type THEN
        UPDATE card_account SET money = @money + amount WHERE id = to_account_id;
    ELSE
        IF from_account_currency_type::text NOT LIKE USD THEN

            SELECT value
            INTO from_account_currency
            FROM (SELECT * FROM json_each(currencies::json)) AS "*2"
            WHERE key LIKE concat('%', from_account_currency_type::text);

            temp_amount := amount / from_account_currency;
        ELSE
            temp_amount := amount;
        END IF;
        IF to_account_currency_type::text != USD THEN
            SELECT value
            INTO to_account_currency
            FROM (SELECT * FROM json_each(currencies::json)) AS "*2"
            WHERE key LIKE concat('%', to_account_currency_type::text);
            raise notice '%', to_account_currency;

            temp_amount := temp_amount * to_account_currency;
            raise notice '%', temp_amount;
        END IF;
        UPDATE card_account SET money = @money + temp_amount WHERE id = to_account_id;
    END IF;

    UPDATE card_account SET money = @money - amount WHERE id = from_account_id;

    RETURN true;
END;
$$ LANGUAGE plpgsql
    SECURITY DEFINER;

DROP FUNCTION IF EXISTS log_card_transaction(to_card BIGINT, from_card BIGINT,
                                             money_amount NUMERIC(10, 3));

CREATE FUNCTION
    log_card_transaction(from_card BIGINT, to_card BIGINT, money_amount NUMERIC(10, 3))
    RETURNS boolean AS
$$
DECLARE
    curr_type currency_type;
BEGIN

    SELECT currency_type
    INTO curr_type
    FROM card
             JOIN card_account ca on ca.id = card.account_id
    WHERE card.id = from_card;

    INSERT INTO card_transaction(to_card_id, from_card_id, amount, currency)
    VALUES (to_card, from_card, money_amount, curr_type);

    RETURN true;
END;
$$ LANGUAGE plpgsql
    SECURITY DEFINER;

DROP FUNCTION IF EXISTS log_account_transaction(from_account BIGINT, to_account BIGINT, money_amount NUMERIC(10, 3));

CREATE FUNCTION
    log_account_transaction(from_account BIGINT, to_account BIGINT, money_amount NUMERIC(10, 3))
    RETURNS boolean AS
$$
DECLARE
    curr_type currency_type;
BEGIN

    SELECT currency_type
    INTO curr_type
    FROM card_account
    WHERE card_account.id = from_account;

    INSERT INTO account_transaction(to_account_id, from_account_id, amount, currency)
    VALUES (to_account, from_account, money_amount, curr_type);

    RETURN true;
END;
$$ LANGUAGE plpgsql
    SECURITY DEFINER;