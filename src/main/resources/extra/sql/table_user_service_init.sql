create table user_service (
service_id int,
user_id_benefit int,
service_accepted varchar(15),
foreign key (service_id) references service(id),
foreign key (user_id_benefit) references users(id)
);