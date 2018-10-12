create database if not exists paintusers;
use paintusers;
drop table if exists currencies;
create table currencies (
  c_id int AUTO_INCREMENT,
  c_name varchar(20),
  c_trading tinyint,
  c_b_trading tinyint,
  c_f_trading tinyint,
  PRIMARY KEY (c_id));
  create index c_name
  on currencies(c_name);

drop table if exists wallets;
create table wallets (
	wallet_id int AUTO_INCREMENT,
	user_name varchar(20),
	c_name varchar(20), 
	balance decimal(8, 2),
	PRIMARY KEY (wallet_id));
create index c_name
on wallets(c_name);

drop table if exists orders;
create table orders (
	order_id int AUTO_INCREMENT,
	owner_name varchar(20),
	wantname varchar(20),
	wantamount decimal(8,2),
	givename varchar(20),
	giveamount decimal(8,2),
	PRIMARY KEY (order_id));
create index wantname
on orders(wantname);
create index givename
on orders(givename);

drop table if exists trades;
create table trades (
	order_id int,
	owner_name varchar(20),
	counter_party varchar(20),
	wantname varchar(20),
	wantamount decimal(8,2),
	givename varchar(20),
	giveamount decimal(8,2),
	execution_time datetime,
PRIMARY KEY (order_id));

drop table if exists news;
create table news (
  headline varchar(140),
  eventtime datetime
);

drop table if exists players;
create table players (
  user_id int AUTO_INCREMENT,
  user_name varchar(20),
  mode int,
PRIMARY KEY (user_id));

drop table if exists loans;
create table loans (
	loan_id int AUTO_INCREMENT,
	loan_type tinyint,
	owner_name varchar(20),
	borrower_name varchar(20),
	loan_currency varchar(20),
	loan_amount decimal(8,2) unsigned,
	initial_margin decimal(8,2) unsigned,
	interest_frequency int unsigned,
	interest_currency varchar(20),
	interest_amount decimal(8,2) unsigned,
	expiration bigint unsigned,
	last_payment timestamp,
	active tinyint unsigned,
	PRIMARY KEY (loan_id));

drop table if exists futures;
create table futures (
  contract_id int AUTO_INCREMENT,
  contract_type tinyint,
  creator varchar(20),
  consumer varchar(20),
  wantname varchar(20),
  wantamount decimal(8,2) unsigned,
  givename varchar(20),
  giveamount decimal(8,2) unsigned,
  expiration timestamp,
  active tinyint,
  PRIMARY KEY (contract_id));

