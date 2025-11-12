create table colocation_user (
colocation_id int,
user_id int,
foreign key (colocation_id) references colocation(id),
foreign key (user_id) references users(id)
);