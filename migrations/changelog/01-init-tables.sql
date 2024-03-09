--changeset RuslanHkmoff:create-link-table
create table if not exists link
(
    id            bigint,
    url           varchar(2048)            NOT NULL,
    visited_at    timestamp with time zone NOT NULL,
    updated_at    timestamp with time zone NOT NULL,
    updates_count integer                  NOT NULL,

    unique (url),
    primary key (id)
);


--changeset RuslanHkmoff:create-chat-table
create table if not exists chat
(
    id         bigint,
    created_at timestamp with time zone DEFAULT NOW(),

    primary key (id)
);


--changeset RuslanHkmoff:create-subscriptions-table
create table if not exists subscriptions
(
    id      bigint generated always as identity,
    link_id bigint references link (id)                   NOT NULL,
    chat_id bigint references chat (id) ON DELETE CASCADE NOT NULL,

    primary key (id)
);
