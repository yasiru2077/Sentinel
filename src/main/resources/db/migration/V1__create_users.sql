CREATE TABLE IF NOT EXISTS users
(
    id uuid primary key default gen_random_uuid(),
    username text not null unique,
    email varchar(254) not null unique,
    password_hash text not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);