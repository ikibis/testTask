create table if not exists notes
(
    id          serial primary key not null,
    title       varchar(200),
    description varchar(1500)
);
create index idx_title_description
    on notes (title, description);