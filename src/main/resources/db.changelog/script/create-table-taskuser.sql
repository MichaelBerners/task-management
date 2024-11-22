--liquibase formatted sql
--changeset belonogov:1
CREATE TABLE task_users
(
    id uuid,
    constraint task_user_pk  primary key (id),
    first_name varchar(150) not null,
    last_name varchar(150) not null,
    task_user_role varchar(150) not null,
    email varchar(150) unique not null,
    password varchar(150) not null
)
--rollback drop table task_users