create table users (
id int not null GENERATED always as identity,
email varchar(255) not null,
password varchar(255) not null,
firstname varchar(255) not null,
lastname varchar(255) not null,
primary key (id),
constraint users_un unique (email),
);