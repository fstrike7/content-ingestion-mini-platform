Contexto
- Se agrega una receta de Docker Compose para levantar Redis y el backend en contenedores, evitando fallos de conexión a Redis al consumir los endpoints cacheados.
- Se incorpora un Dockerfile multi-stage para construir la imagen del backend con JDK 21 y empaquetarla en una imagen liviana de runtime.

Diseño
- `docker-compose.yml` define dos servicios: `redis` (imagen oficial, volumen `redis_data`, healthcheck) y `backend` (construido desde `backend/Dockerfile`, depende de Redis saludable).
- El Dockerfile usa `maven:3.9.9-eclipse-temurin-21` para compilar sin tests y `eclipse-temurin:21-jre` como runtime, exponiendo el puerto 8080.
- Variables de entorno: `SPRING_DATA_REDIS_HOST` y `SPRING_DATA_REDIS_PORT` se inyectan en el contenedor del backend; las properties ahora admiten override por env.
- Makefile con targets `dev-up` (modo interactivo) y `up` (modo detached) para orquestar Compose; `down`/`dev-down` limpian orphans y `logs` sigue backend/redis.

Endpoints
- Sin cambios: se mantienen `POST /api/content/ingest` y `GET /api/content/{id}`.

Modelo
- Sin cambios en DTOs ni enums; solo wiring de infraestructura.

Errores
- Si Redis no está arriba o saludable, `backend` no arranca (healthcheck + `depends_on`) o lanzará `RedisConnectionFailureException`; resolver levantando `redis` con Compose.
- Si el puerto 8080 o 6379 está ocupado en el host, ajustar mapeos en `docker-compose.yml` o exportar otros puertos antes de ejecutar Makefile.

Tests
- No se añadieron tests nuevos (no hubo cambios en lógica de negocio). El build de la imagen omite tests (`-DskipTests`); correr `./mvnw test` localmente antes de construir si se desea validar.
