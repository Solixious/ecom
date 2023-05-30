CREATE TABLE IF NOT EXISTS tbl_roles (
    id      SERIAL PRIMARY KEY,
    role    VARCHAR(16) UNIQUE NOT NULL,
    create_date     TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date     TIMESTAMP NOT NULL DEFAULT NOW(),
    active          BOOLEAN DEFAULT true
);

INSERT INTO tbl_roles (id, role) VALUES (0, 'ROLE_ADMIN');
INSERT INTO tbl_roles (id, role) VALUES (1, 'ROLE_USER');