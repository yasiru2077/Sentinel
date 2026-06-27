CREATE TABLE IF NOT EXISTS tasks
(
    id          uuid         primary key default gen_random_uuid(),
    user_id     uuid         not null references users (id) on delete cascade,
    title       varchar(500) not null,
    description varchar(2000),
    created_at  timestamptz  not null default now(),
    updated_at  timestamptz  not null default now()
);