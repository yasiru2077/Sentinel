CREATE TABLE IF NOT EXISTS tasks
(
    id          uuid primary key default gen_random_uuid(),
    project_id  uuid         not null references projects (id) on delete cascade,
    stage_id    uuid         not null references stages (id) on delete restrict,
    created_by  uuid         not null references users (id) on delete restrict,
    priority    varchar(20)  not null,
    title       varchar(500) not null,
    description varchar(2000),
    created_at  timestamptz  not null default now(),
    updated_at  timestamptz  not null default now()
);