CREATE TABLE IF NOT EXISTS tbl_user_roles (
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER NOT NULL,
    role_id     INTEGER NOT NULL,
    create_date     TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date     TIMESTAMP NOT NULL DEFAULT NOW(),
    active          BOOLEAN DEFAULT true
);