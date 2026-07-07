CREATE TABLE IF NOT EXISTS projects
(
    id           uuid primary key default gen_random_uuid(),
    company_id   uuid         not null references companies (id) on delete cascade,
    created_by   uuid         not null references users (id) on delete restrict,
    project_name varchar(255) not null,
    description  varchar(2000),
    created_at   timestamptz  not null default now(),
    updated_at   timestamptz  not null default now(),
    constraint uk_projects_company_name unique (company_id, project_name)
);

CREATE TABLE IF NOT EXISTS project_members
(
    id         uuid primary key default gen_random_uuid(),
    project_id uuid        not null references projects (id) on delete cascade,
    user_id    uuid        not null references users (id) on delete cascade,
    added_by   uuid        not null references users (id) on delete restrict,
    created_at timestamptz not null default now(),
    constraint uk_project_members_project_user unique (project_id, user_id)
);