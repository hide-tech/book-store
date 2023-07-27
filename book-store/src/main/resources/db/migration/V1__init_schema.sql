create table books (
    id bigserial primary key not null,
    title varchar(150) not null,
    author varchar(150) not null,
    description varchar(150)
);

CREATE SEQUENCE books_seq
start with 6
increment by 1
minvalue 0
maxvalue 1000000
cycle;