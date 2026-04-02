CREATE TYPE http_method AS ENUM ('GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS', 'HEAD');

create table users
(
    id              bigserial primary key,
    username        varchar(255) not null unique,
    email           varchar(255) not null unique,
    hashed_password varchar(255) not null,
    created_at      timestamp with time zone default now(),
    updated_at      timestamp with time zone default now()
);

create table api
(
    id         bigserial primary key,
    user_id    bigint       not null references users (id) on delete cascade,
    name       varchar(255) not null,
    url        varchar(255) not null,
    method     http_method  not null,
    status     varchar(20)  not null    default 'UNKNOWN',
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now()
);

create table checks
(
    id            bigserial primary key,
    api_id        bigint not null references api (id) on delete cascade,
    response_time bigint    not null,
    response_code int    not null,
    checked_at    timestamp with time zone default now()
);