create table roles_entity (role_id varchar(255) not null, role varchar(255), primary key (role_id));
create table user_roles (user_id varchar(255) not null, role_id varchar(255) not null, primary key (user_id, role_id));
create table users (user_id varchar(255) not null, created_at timestamp(6), date_of_birth date, email varchar(255), name varchar(255), oauth_strategy JSON, password varchar(255), phone_number varchar(255), updated_at timestamp(6), username varchar(255), primary key (user_id));
alter table if exists user_roles add constraint FKbmivxcqs3li7bawhufdf4oedx foreign key (role_id) references roles_entity;
alter table if exists user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users;
