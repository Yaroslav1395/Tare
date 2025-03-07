create table Zavod.capacity (
capacity_from integer,
capacity_to integer,
id integer generated by default as identity,
primary key (id)
);

create table Zavod.categories (
id integer generated by default as identity,
name varchar(255),
category_image varchar,
primary key (id)
);

create table Zavod.certificates (
id integer generated by default as identity,
description varchar(2000),
name varchar(255),
certificate_image varchar,
certificate_image_type varchar(10),
certificate_image_kg varchar(255),
certificate_image_name_kg VARCHAR(255),
certificate_image_type_kg VARCHAR(255),
primary key (id)
);

create table Zavod.characteristics (
id integer generated by default as identity,
name varchar(255),
primary key (id)
);

create table Zavod.colors (
id integer generated by default as identity,
hex_code varchar(255),
name varchar(255),
primary key (id)
);

create table Zavod.delivery_prices (
district_id integer,
capacity_id integer,
id integer generated by default as identity,
price integer,
primary key (id)
);

create table Zavod.district (
division_id integer,
id integer generated by default as identity,
name varchar(255),
primary key (id)
);

create table Zavod.division (
id integer generated by default as identity,
name varchar(255),
primary key (id)
);

create table Zavod.images (
color_id integer,
id integer generated by default as identity,
product_id integer,
product_image varchar,
product_image_type varchar(10),
product_image_name VARCHAR(255),
primary key (id)
);

create table Zavod.notices (
id integer generated by default as identity,
is_active boolean,
created_time timestamp(6),
updated_time timestamp(6),
description varchar(2000),
title varchar(255),
notice_image varchar,
image_type  varchar(10),
primary key (id)
);

create table Zavod.partners (
id integer generated by default as identity,
is_active boolean,
created_time timestamp(6),
updated_time timestamp(6),
description varchar(4000),
name varchar(255),
logo_image varchar,
logo_image_type varchar(10),
product_image varchar,
product_image_type varchar(10),
primary key (id)
);

create table Zavod.product_characteristics (
characteristic_id integer,
id integer generated by default as identity,
product_id integer,
val DOUBLE,
primary key (id)
);

create table Zavod.products (
id integer generated by default as identity,
id_from_factory_bd integer unique,
subcategory_id integer,
name varchar(255),
description varchar(2000),
primary key (id)
);

create table Zavod.roles (
id integer generated by default as identity,
role varchar(255),
primary key (id)
);

create table Zavod.subcategories (
category_id integer not null,
id integer generated by default as identity,
name varchar(255),
subcategory_image varchar,
primary key (id)
);

create table Zavod.user_role (
role_id integer not null,
user_id integer not null,
primary key (role_id, user_id)
);

create table Zavod.users (
id integer generated by default as identity,
username varchar(255),
name varchar(255),
password varchar(255),
patronymic varchar(255),
phone_number varchar(255),
surname varchar(255),
token_uuid varchar(255),
is_deleted boolean,
primary key (id)
);

create table Zavod.vacancies (
id integer generated by default as identity,
description varchar(2000),
name varchar(255),
offer varchar(2000),
primary key (id)
);

alter table if exists Zavod.delivery_prices
add constraint FKjgms7bnhqqrafcruaiovimv1s
foreign key (capacity_id) references Zavod.capacity;

alter table if exists Zavod.delivery_prices
add constraint FKcr7htidk9oscj0lpe71w4wkik
foreign key (district_id) references Zavod.district;

alter table if exists Zavod.district
add constraint FK78g8m793eebrogjuuey668ihu
foreign key (division_id) references Zavod.division;

alter table if exists Zavod.images
add constraint FKmghfeyqfawt5x7vvenk9jh9eq
foreign key (color_id) references Zavod.colors;

alter table if exists Zavod.images
add constraint FKghwsjbjo7mg3iufxruvq6iu3q
foreign key (product_id) references Zavod.products;

alter table if exists Zavod.product_characteristics
add constraint FK4j2eyeedihx9i4dg3vgawqqls
foreign key (characteristic_id) references Zavod.characteristics;

alter table if exists Zavod.product_characteristics
add constraint FKlwuvy71q239qxmgor4u6ni4ej
foreign key (product_id) references Zavod.products;

alter table if exists Zavod.products
add constraint FKappm930ygdfv4qkkhc05pbr5s
foreign key (subcategory_id) references Zavod.subcategories;

alter table if exists Zavod.subcategories
add constraint FKiborb6ptvy1t1n3v6klb56l5s
foreign key (category_id) references Zavod.categories;

alter table if exists Zavod.user_role
add constraint FKt7e7djp752sqn6w22i6ocqy6q
foreign key (role_id) references Zavod.roles;

alter table if exists Zavod.user_role
add constraint FKj345gk1bovqvfame88rcx7yyx
foreign key (user_id) references Zavod.users;