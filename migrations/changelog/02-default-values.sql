--changeset RuslanHkmoff:add-default-value
alter table link
    alter column id set NOT NULL,
    alter column visited_at set default now(),
    alter column updated_at drop not null,
    alter column updates_count drop not null;

alter table chat
    alter column created_at set not null
