CREATE TABLE IF NOT EXISTS join_requests
(
    id         uuid primary key default gen_random_uuid(),
    user_id    uuid        not null references users (id) on delete cascade,
    company_id uuid        not null references companies (id) on delete cascade,
    status     varchar(50) not null default 'PENDING',
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint uk_join_requests_user_company unique (user_id, company_id)
);