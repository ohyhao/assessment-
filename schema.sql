create database posting;

use posting;

create table postings (

	posting_id varchar(8),
    posting_date varchar(64) not null,
	name varchar(128) not null,
	email varchar(128) not null,
    phone varchar(64) default '',
	title varchar(256),
	description text not null,
	image varchar(256) not null,

	primary key(posting_id)
);