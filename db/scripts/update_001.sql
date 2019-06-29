create table if not exists notes
(
    id          serial primary key not null,
    title       varchar(200),
    description varchar(1500)
);