CREATE TABLE IF NOT EXISTS TASKS (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERNCES users(id) ON DELETE CASCADE,
    title VARCHAR(500) NPOT NULL,
    description VARCHAR(2000),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX  idx_tasks_user_id ON tasks(user_id);