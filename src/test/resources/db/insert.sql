insert into task_users (id, first_name, last_name, task_user_role, email, password)
values ('022460aa-5f6a-4b0a-8c53-b336696e3a69', 'testFirstName', 'testLastName', 'ADMIN', 'test@mail.ru', 'test111password');
insert into task_users (id, first_name, last_name, task_user_role, email, password)
values ('ec9cf692-2235-49f2-97e4-9f103df3e77f', 'testFirstName', 'testLastName', 'ADMIN', 'testtest@mail.ru', 'test111password');
insert into tasks (id, heading, description, task_status, priority, author_id)
values ('d0a8292f-0cee-4a77-bf40-9e4d28b10d0b', 'heading1', 'description1', 'IN_PROGRESS', 'AVERAGE', '022460aa-5f6a-4b0a-8c53-b336696e3a69');
insert into tasks (id, heading, description, task_status, priority, author_id)
values ('cb605fd8-f036-4fd1-8037-4c15b728e566', 'heading2', 'description2', 'IN_PROGRESS', 'AVERAGE', '022460aa-5f6a-4b0a-8c53-b336696e3a69');
insert into tasks (id, heading, description, task_status, priority, author_id)
values ('bf2a9867-813a-4ca6-938e-582606790b28', 'heading3', 'description3', 'IN_PROGRESS', 'AVERAGE', '022460aa-5f6a-4b0a-8c53-b336696e3a69');
insert into tasks (id, heading, description, task_status,  priority, author_id)
values ('d32ba584-00d6-40e0-b273-a3a7079302ef', 'heading4', 'IN_PROGRESS', 'description4', 'AVERAGE', 'ec9cf692-2235-49f2-97e4-9f103df3e77f');