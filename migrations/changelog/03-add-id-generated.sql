--changeset RuslanHkmoff:add-default-value
alter table link
    alter column id add generated always as identity
