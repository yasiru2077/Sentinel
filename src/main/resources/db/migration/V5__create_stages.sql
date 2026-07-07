CREATE TABLE IF NOT EXISTS stages
(
    id          uuid primary key default gen_random_uuid(),
    project_id  uuid         not null references projects (id) on delete cascade,
    created_by  uuid         not null references users (id) on delete restrict,
    stage_name  varchar(100) not null,
    order_index integer      not null,
    created_at  timestamptz  not null default now(),
    updated_at  timestamptz  not null default now(),
    constraint uk_stages_project_order unique (project_id, order_index),
    constraint uk_stages_project_name unique (project_id, stage_name)
);