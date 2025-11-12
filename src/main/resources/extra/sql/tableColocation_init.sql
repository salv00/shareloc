create table colocation (
id int not null GENERATED always as identity,
name varchar(255),
user_id int,
constraint colocation_pkey primary key(id),
constraint colocation_un unique (name)
);