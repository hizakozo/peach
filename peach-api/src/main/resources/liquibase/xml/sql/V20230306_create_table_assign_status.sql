CREATE TABLE IF NOT EXISTS assign_status
(
    item_id     uuid                                NOT NULL,
    status_id   uuid                                NOT NULL,
    assigned_by varchar(100)                        NOT NULL,
    assigned_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (item_id, assigned_at),
    FOREIGN KEY (item_id) REFERENCES items (item_id),
    FOREIGN KEY (status_id) REFERENCES statues (status_id)
);
