create table user_vote (
user_id int,
vote_id int,
foreign key (user_id) references users(id),
foreign key (vote_id) references vote(id)
);