CREATE TABLE IF NOT EXISTS companies
(
    id            uuid primary key default gen_random_uuid(),
    created_by_id uuid         not null references users (id) on delete restrict,
    company_name  varchar(200) not null unique,
    created_at    timestamptz  not null default now(),
    updated_at    timestamptz  not null default now()
);

CREATE TABLE IF NOT EXISTS roles
(
    id           uuid primary key default gen_random_uuid(),
    user_id      uuid         not null references users (id) on delete cascade,
    company_id   uuid         not null references companies (id) on delete cascade,
    job_position varchar(100) not null,
    role_type    varchar(20)  not null,
    created_at   timestamptz  not null default now(),
    updated_at   timestamptz  not null default now(),
    constraint uk_roles_user_company unique (user_id, company_id)
);