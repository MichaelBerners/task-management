--liquibase formatted sql
--changeset belonogov:2
create table tasks
(
    id uuid,
    constraint tasks_pk primary key (id),
    heading varchar(300),
    description text,
    task_status varchar(100) not null,
    priority varchar(300),
    author_id uuid not null,
    constraint tasks_task_users_author_fk foreign key (author_id) references task_users(id),
    executor_id uuid,
    constraint tasks_task_users_executor_id foreign key (executor_id) references  task_users(id)
)
--rollback drop table tasks