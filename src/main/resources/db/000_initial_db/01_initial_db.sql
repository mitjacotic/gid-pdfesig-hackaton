create table if not exists pdf (
    id                    uuid PRIMARY KEY,
    gid_uuid              uuid NOT NULL,
    file_path             varchar(255) NOT NULL,
    file_name             varchar(100) NOT NULL,
    created_at            timestamp,
    updated_at            timestamp
);
