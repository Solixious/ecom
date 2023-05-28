CREATE OR REPLACE FUNCTION update_update_date()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.update_date = now();
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_update_date_table1
    BEFORE UPDATE ON tbl_users
    FOR EACH ROW
EXECUTE FUNCTION update_update_date();

CREATE TRIGGER trigger_update_update_date_table1
    BEFORE UPDATE ON tbl_roles
    FOR EACH ROW
EXECUTE FUNCTION update_update_date();

CREATE TRIGGER trigger_update_update_date_table1
    BEFORE UPDATE ON tbl_user_roles
    FOR EACH ROW
EXECUTE FUNCTION update_update_date();

CREATE TRIGGER trigger_update_update_date_table1
    BEFORE UPDATE ON tbl_refresh_token
    FOR EACH ROW
EXECUTE FUNCTION update_update_date();