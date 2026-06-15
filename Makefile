.PHONY: build up down logs restart clean

build:
	docker compose --env-file .env build

up:
	docker compose --env-file .env down
	docker compose --env-file .env up -d

up-build:
	docker compose --env-file .env down
	docker compose --env-file .env up -d --build

down:
	docker compose --env-file .env down

logs:
	docker compose --env-file .env logs -f app

restart:
	docker compose --env-file .env restart app

clean:
	docker compose --env-file .env down -v --rmi all

db-shell:
	docker compose exec db psql -U root -d sentinel_db

db-flush:
	docker compose --env-file .env exec db psql -U root -d sentinel_db -c "TRUNCATE TABLE users CASCADE;"