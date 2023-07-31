CREATE TABLE IF NOT EXISTS tbl_merchant (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(64) UNIQUE NOT NULL,
    icon            VARCHAR(128),
    create_date     TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date     TIMESTAMP NOT NULL DEFAULT NOW(),
    active          BOOLEAN DEFAULT true
);

CREATE TRIGGER trigger_update_update_date_table1
    BEFORE UPDATE ON tbl_merchant
    FOR EACH ROW
EXECUTE FUNCTION update_update_date();