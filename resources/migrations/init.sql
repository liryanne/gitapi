CREATE TABLE IF NOT EXISTS public.user
    (id int not null primary key,
    username varchar(30) not null);

CREATE TABLE IF NOT EXISTS public.repository
    (id int not null primary key,
    name varchar(50) not null,
    user_id int not null,
    url varchar(60),
    size int,
    stargazers int,
    watchers int,
    type_access varchar(10),
    updated_at varchar(20),
    created_at varchar(20),
CONSTRAINT fk_user
    FOREIGN KEY(user_id)
    REFERENCES public.user(id));
