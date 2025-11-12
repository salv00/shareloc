create table vote (
id int not null GENERATED always as identity,
vote varchar(10),
service_id int,
constraint vote_pkey primary key(id),
foreign key (service_id) references service(id)
);