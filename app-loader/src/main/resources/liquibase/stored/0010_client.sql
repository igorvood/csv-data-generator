create table client
(
    id varchar not null,
    constraint client_pk primary key (id),
    name varchar not null,
    salary numeric not null,
    isWorker bool not null,
    isMarried bool not null,
    birthDate timestamp not null
)