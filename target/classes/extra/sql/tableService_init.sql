create table user_service (
id int not null GENERATED always as identity,
title varchar(255),
description varchar(255),
cost int,
user_id int,
colocation_id int,
service_approved varchar(30),
constraint service_pkey primary key (id),
foreign key (user_id) references users(id),
foreign key (colocation_id) references colocation(id)
);