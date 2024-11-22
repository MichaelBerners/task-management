--liquibase formatted sql
--changeset belonogov:3
create table comments
(
    id uuid,
    constraint comments_pk primary key (id),
    comment text not null,
    task_id uuid not null,
    constraint comments_tasks_fk foreign key (task_id) references tasks (id)
)
--rollback drop table comments