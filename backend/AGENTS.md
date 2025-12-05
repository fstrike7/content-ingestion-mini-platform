# AGENT INSTRUCTIONS – BACKEND (Spring Boot)

Este archivo le explica a cualquier agente LLM cómo entender y trabajar en el backend de este proyecto.

---

## 1. Propósito del proyecto

Este backend implementa una **mini plataforma de ingestión de contenido**:

- Exponer APIs para **ingestar contenido** (metadata, URLs, etc.).
- Enviar las solicitudes a una **cola** para ser procesadas de forma asíncrona.
- Procesar los mensajes con un **worker** que:
    - Llama a un **servicio externo en Python** para procesamiento pesado.
    - Persiste el estado del contenido en una **base de datos**.
    - Cachea lecturas frecuentes en **Redis**.
- Exponer APIs para consultar el **estado y datos procesados** del contenido.

Piensa en este proyecto como una versión simplificada de una **media ingestion pipeline** (tipo Disney/streaming).

---

## 2. Stack y contexto técnico

- Lenguaje: **Java**
- Framework: **Spring Boot**
- Dependencias principales:
    - Spring Web
    - Spring Boot Actuator
    - Spring Data JPA
    - Spring Cache + Spring Data Redis
    - Spring Validation
    - (Opcional) Spring Cloud AWS / cliente SQS
- Infra (en `docker-compose.yml`):
    - Postgres
    - Redis
    - Localstack (SQS)
    - Servicio Python (otro repositorio/módulo)

---

## 3. Estructura esperada del backend

Ruta esperada del backend:

```text
backend/
├─ AGENTS.md
├─ pom.xml
└─ src/main/java/com/faustino/content/
   ├─ ContentIngestionApplication.java
   ├─ config/
   ├─ controller/
   ├─ service/
   ├─ repository/
   ├─ model/
   ├─ messaging/
   ├─ interceptor/
   ├─ exception/
   ├─ dto/
   └─ ...
```

 ## 4. Comportamiento del agente- Mantener arquitectura por capas.- Documentar cada cambio en backend/docs/.- Generar tests para nuevas funcionalidades.- Mantener claridad, evitar lógica en controllers.
 
## 5. Documentación
 Cada cambio debe crear/actualizar un archivo en backend/docs/ siguiendo:
 Contexto, Diseño, Endpoints, Modelo, Errores, Tests.
 
## 6. Testing y cobertura- Framework: JUnit 5, Mockito.- Tests unitarios + controller tests + integración.- Cobertura razonable de la lógica expuesta.- Si hay ramas complejas, cubrirlas.
 
## 7. Logging, errores y observabilidad- Logging estructurado.- Correlation ID desde interceptor.- Errores centralizados en ControllerAdvice.- Actuator habilitado.
 
## 8. Integraciones externas- SQS: producers/consumers en messaging/.- Python: WebClient HTTP.- DB: Spring Data JPA.- Cache: Redis.
 
## 9. Antipatrones prohibidos- Lógica en controllers.- Nombres genéricos sin contexto.- Crear endpoints sin tests.- Mezclar capas.
 
## 10. Flujo de trabajo del agente
 1. Leer AGENTS.md.
 2. Proponer cambios.
 3. Implementar código por capas.
 4. Agregar tests.
 5. Documentar en backend/docs/.
 6. Sugerir comandos si aplica