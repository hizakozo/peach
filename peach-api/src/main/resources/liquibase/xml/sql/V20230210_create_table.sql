CREATE TABLE IF NOT EXISTS users
(
    user_id    uuid         NOT NULL,
    user_name  varchar(100) NOT NULL,
    birth_day  date      DEFAULT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    changed_at timestamp DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp DEFAULT null,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS google_authentications
(
    user_id      uuid         NOT NULL,
    principal_id varchar(225) NOT NULL,
    mail_address varchar(100) NOT NULL,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    changed_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    deleted_at   timestamp DEFAULT null,
    PRIMARY KEY (user_id, principal_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    UNIQUE (principal_id),
    UNIQUE (mail_address)
);

CREATE TABLE IF NOT EXISTS groups
(
    group_id      uuid                                   NOT NULL,
    group_name    varchar(100)                           NOT NULL,
    group_remarks varchar(225) DEFAULT NULL,
    created_at    timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by    uuid                                   NOT NULL,
    changed_at    timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by    uuid                                   NOT NULL,
    deleted_by    uuid         DEFAULT null,
    deleted_at    timestamp    DEFAULT null,
    PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS group_entry_qualifications
(
    group_id        uuid NOT NULL,
    invitation_code uuid NOT NULL,
    PRIMARY KEY (group_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE IF NOT EXISTS user_groups
(
    group_id uuid NOT NULL,
    user_id  uuid NOT NULL,
    PRIMARY KEY (group_id, user_id),
    UNIQUE (group_id, user_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id      uuid                                   NOT NULL,
    group_id         uuid                                   NOT NULL,
    category_name    varchar(100)                           NOT NULL,
    category_remarks varchar(225) DEFAULT null              NOT NULL,
    created_at       timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by       uuid                                   NOT NULL,
    changed_at       timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by       uuid                                   NOT NULL,
    PRIMARY KEY (category_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE IF NOT EXISTS items
(
    item_id      uuid                                   NOT NULL,
    category_id  uuid                                   NOT NULL,
    item_name    varchar(100)                           NOT NULL,
    item_remarks varchar(225) DEFAULT null              NOT NULL,
    created_at   timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by   uuid                                   NOT NULL,
    changed_at   timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by   uuid                                   NOT NULL,
    deleted_by   uuid         DEFAULT null,
    deleted_at   timestamp    DEFAULT null,
    PRIMARY KEY (item_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE IF NOT EXISTS statues
(
    status_id    uuid                                   NOT NULL,
    category_id  uuid                                   NOT NULL,
    status_name  varchar(100)                           NOT NULL,
    status_color varchar(100) DEFAULT '#FFFFFF'         NOT NULL,
    created_at   timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by   uuid                                   NOT NULL,
    changed_at   timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by   uuid                                   NOT NULL,
    deleted_by   uuid         DEFAULT null,
    deleted_at   timestamp    DEFAULT null,
    PRIMARY KEY (status_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE IF NOT EXISTS item_statues
(
    item_id    uuid                                NOT NULL,
    status_id  uuid                                NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by uuid                                NOT NULL,
    changed_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by uuid                                NOT NULL,
    PRIMARY KEY (status_id),
    FOREIGN KEY (item_id) REFERENCES items (item_id),
    FOREIGN KEY (status_id) REFERENCES statues (status_id)
);