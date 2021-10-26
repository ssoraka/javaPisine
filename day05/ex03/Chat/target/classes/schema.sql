
create table "users"
(
    id serial,
    login varchar not null,
    password varchar,
    rooms_id int[],
    chatrooms_id int[]
);

create unique index users_id_uindex
	on "users" (id);

alter table "users"
    add constraint users_pk
        primary key (id);

create table chatrooms
(
    id serial,
    name varchar not null,
    owner_id int
        constraint chatrooms_users_id_fk
            references "user",
    messages_id int[]
);

create unique index chatrooms_id_uindex
	on chatrooms (id);

alter table chatrooms
    add constraint chatrooms_pk
        primary key (id);

create table messages
(
    id serial,
    author int
        constraint messages_users_user_id_fk
            references users,
    room int
        constraint messages_chatrooms_id_fk
            references chatrooms,
    mText varchar,
    dateT timestamp
);

create unique index messages_id_uindex
	on messages (id);

alter table messages
    add constraint messages_pk
        primary key (id);