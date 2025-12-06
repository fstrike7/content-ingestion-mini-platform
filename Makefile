COMPOSE ?= docker compose

.PHONY: dev-up dev-down up down logs

dev-up:
	$(COMPOSE) up --build

dev-down:
	$(COMPOSE) down --remove-orphans

up:
	$(COMPOSE) up --build -d

down:
	$(COMPOSE) down --remove-orphans

logs:
	$(COMPOSE) logs -f backend redis
