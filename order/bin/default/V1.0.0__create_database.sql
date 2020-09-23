--
-- SCRIPT DE CRIAÇÃO DOS OBJETOS DO BANCO DE DADOS
--

create table tb_client (
	id uuid not null, 
	email varchar(255) not null, 
	name varchar(255), 
	primary key (id)
);

create table tb_event (
	id uuid not null,
	type varchar(255),
	data json,
	primary key (id)
);

create table tb_order (
	id uuid not null, 
	orderNumber serial, 
	total float8, 
	client_id uuid, 
	primary key (id)
);

create table tb_order_product (
	id uuid not null, 
	price float8, 
	order_id uuid not null, 
	product_id uuid, 
	primary key (id)
);

create table tb_product (
	id uuid not null, 
	name varchar(255), 
	price float8, 
	primary key (id)
);

alter table if exists tb_client add constraint uk_client_email unique (email);
alter table if exists tb_order add constraint fk_order_client foreign key (client_id) references tb_client;
alter table if exists tb_order_product add constraint fk_order_product_order foreign key (order_id) references tb_order;
alter table if exists tb_order_product add constraint fk_order_product_product foreign key (product_id) references tb_product;

insert into tb_client values ('bb8e800c-cb85-4409-ad1a-bc6a0060396c', 'gurus.gui@gmail.com', 'Guilherme Faht');
insert into tb_product values ('c5650dc5-d248-4da1-ba19-5836256be174', 'Product A', 100.99);