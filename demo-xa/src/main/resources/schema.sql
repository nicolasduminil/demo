drop table if exists message;

create table message (
  id serial primary key,
  contents varchar(1024) not null
);
