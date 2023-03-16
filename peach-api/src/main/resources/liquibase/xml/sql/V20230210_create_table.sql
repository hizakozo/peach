CREATE TABLE IF NOT EXISTS groups
(
    group_id      uuid                                   NOT NULL,
    group_name    varchar(100)                           NOT NULL,
    group_remarks varchar(225) DEFAULT NULL,
    created_at    timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by    varchar(100)                           NOT NULL,
    changed_at    timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by    varchar(100)                           NOT NULL,
    PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS delete_group
(
    group_id   uuid                                   NOT NULL,
    deleted_at timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_by varchar(100) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (group_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
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
    group_id uuid         NOT NULL,
    user_id  varchar(100) NOT NULL,
    PRIMARY KEY (group_id, user_id),
    UNIQUE (group_id, user_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id      uuid                                   NOT NULL,
    group_id         uuid                                   NOT NULL,
    category_name    varchar(100)                           NOT NULL,
    category_remarks varchar(225) DEFAULT null              NOT NULL,
    created_at       timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by       varchar(100)                           NOT NULL,
    changed_at       timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by       varchar(100)                           NOT NULL,
    PRIMARY KEY (category_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE IF NOT EXISTS delete_category
(
    category_id uuid                                   NOT NULL,
    deleted_at  timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_by  varchar(100) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (category_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE IF NOT EXISTS items
(
    item_id      uuid                                   NOT NULL,
    category_id  uuid                                   NOT NULL,
    item_name    varchar(100)                           NOT NULL,
    item_remarks varchar(225) DEFAULT null              NOT NULL,
    created_at   timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by   varchar(100)                           NOT NULL,
    changed_at   timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by   varchar(100)                           NOT NULL,
    PRIMARY KEY (item_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE IF NOT EXISTS delete_item
(
    item_id    uuid                                   NOT NULL,
    deleted_at timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_by varchar(100) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (item_id),
    FOREIGN KEY (item_id) REFERENCES items (item_id)
);
CREATE TABLE IF NOT EXISTS statues
(
    status_id    uuid                                   NOT NULL,
    category_id  uuid                                   NOT NULL,
    status_name  varchar(100)                           NOT NULL,
    status_color varchar(100) DEFAULT '#FFFFFF'         NOT NULL,
    created_at   timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by   varchar(100)                           NOT NULL,
    changed_at   timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by   varchar(100)                           NOT NULL,
    PRIMARY KEY (status_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE IF NOT EXISTS delete_status
(
    status_id  uuid                                   NOT NULL,
    deleted_at timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_by varchar(100) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (status_id),
    UNIQUE (status_id),
    FOREIGN KEY (status_id) REFERENCES statues (status_id)
);

CREATE TABLE IF NOT EXISTS assigned_status
(
    item_id     uuid                                NOT NULL,
    status_id   uuid                                NOT NULL,
    assigned_by varchar(100)                        NOT NULL,
    assigned_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (item_id),
    UNIQUE (item_id),
    FOREIGN KEY (item_id) REFERENCES items (item_id),
    FOREIGN KEY (status_id) REFERENCES statues (status_id)
);

CREATE TABLE IF NOT EXISTS invite_group
(
    group_id    uuid                                NOT NULL,
    invite_code varchar(100)                         NOT NULL,
    term_to     timestamp                           NOT NULL,
    invite_at   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    invite_by   varchar(100)                        NOT NULL,
    PRIMARY KEY (group_id),
    UNIQUE (invite_code),
    UNIQUE (group_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);
