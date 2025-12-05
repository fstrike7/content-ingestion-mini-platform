Contexto
- Primeros endpoints para ingestar contenido y consultarlo de forma dummy mientras se implementa la cola y persistencia.
- Se modelan respuestas usando ContentResponse con estados del enum ContentStatus.

Diseño
- Controller delgado (`ContentController`) delega en servicios de dominio.
- `ContentIngestionService` prepara respuestas PENDING y en el futuro enviará a la cola/DB.
- `ContentQueryService` expone lectura cacheable (cache `contentById`) con respuesta dummy y valida el id.
- `WebClientConfig` registra un `WebClient` para el servicio Python (`content.processor.base-url`, default http://localhost:8000).
- `@EnableCaching` se habilita en la aplicación para que la anotación `@Cacheable` funcione.

Endpoints
- POST `/api/content/ingest` → acepta `ContentIngestionRequest { id:int>0, sourceUrl:notBlank, metadata:nonEmpty map<String,String> }`, valida, loguea y responde 202 con `ContentResponse` en estado `PENDING`.
- GET `/api/content/{id}` → consulta `ContentQueryService`, responde 200 con `ContentResponse` dummy (`READY`, metadata demo map). Si el id no es numérico devuelve 400.

Modelo
- `ContentStatus` enum: PENDING, IN_PROGRESS, READY, FAILED.
- `ContentResponse`: id:int, status:ContentStatus, metadata:map<String,String>, processedData:Optional<Boolean>.
- `ContentIngestionRequest`: id:int, sourceUrl:String, metadata:map<String,String>; con validaciones Jakarta.

Errores
- Validaciones de request (Bean Validation) retornan 400 automáticamente.
- IDs no numéricos en GET retornan 400 (`ResponseStatusException`).

Tests
- `ContentControllerTest`: verifica 202 en ingest, validaciones 400 y GET 200.
- `ContentIngestionServiceTest`: valida respuesta PENDING y eco de metadata.
- `ContentQueryServiceTest`: valida dummy READY y error en id inválido.
