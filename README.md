# content-ingestion-mini-platform

## Backend

### Objetivo rápido
- API REST en Spring Boot para ingestar contenido y consultar estado (dummy por ahora).
- Cache habilitada; sin DB/SQS aún.

### Prerrequisitos
- Java 21
- Maven Wrapper (incluido: `./mvnw` en Linux/macOS, `mvnw.cmd` en Windows)

### Compilar y correr tests
- `./mvnw clean test`
  - Los tests usan el perfil `test` (deshabilita autoconfiguración de datasource/JPA/Redis y usa caché en memoria).

### Empaquetar
- `./mvnw clean package`
  - Genera `target/content-ingestion-0.0.1-SNAPSHOT.jar`.

### Ejecutar local
- `./mvnw spring-boot:run`
- Endpoints expuestos:
  - `POST /api/content/ingest` — valida `ContentIngestionRequest` y responde `202 Accepted` con estado `PENDING`.
  - `GET /api/content/{id}` — devuelve respuesta dummy `READY`, cacheable.
- Propiedades útiles:
  - `content.processor.base-url` (URL del servicio Python; default `http://localhost:8000`).

### Observabilidad
- Actuator disponible en `/actuator` (health, info, etc.).

### Próximos pasos previstos
- Integrar SQS/localstack, persistencia en Postgres y cache real en Redis.
- Worker que llame al servicio Python y actualice estados.
