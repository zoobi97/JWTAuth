drop database expensetrackerdb;
drop user expensetracker;
create user expensetracker with password 'password';
create database expensetrackerdb with template=template0 owner=expensetracker;
\connect expensetrackerdb;
alter default privileges grant all on tables to expensetracker;
alter default privileges grant all on sequences to expensetracker;

create table et_users(
    user_id integer primary key not null,
    first_name varchar (30) not null,
    last_name varchar(30) NOT NULL,
    email varchar(30) not null,
    password text not null
);

create table et_categories(
  category_id integer primary key not null,
  user_id integer not null,
  title varchar(30) not null,
  description varchar(30) not null,
  constraint cat_users_fk foreign key (user_id) references et_users(user_id)
);

create table et_transactions(
  transaction_id integer primary key not null,
  category_id integer not null,
  user_id integer not null,
  amount numeric(10,2) not null,
  note varchar(30) not null,
  transaction_date bigint not null,
  constraint tr_cat_fk foreign key (category_id) references et_categories(category_id),
  constraint tr_user_fk foreign key (user_id) references et_users(user_id)
);

create sequence et_users_seq increment by 1 start with 1;
create sequence et_categories_seq increment by 1 start with 1;
create sequence et_transactions_seq increment by 1 start with 1000;





