create table chatcolocation (
id int not null GENERATED always as identity,
sendername varchar(255),
receivername varchar(255),
message varchar(255),
date varchar(15),
status varchar(20),
constraint chat_pkey primary key (id)
);