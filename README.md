"# content-ingestion-mini-platform" 


Diagrama del resultado esperado:

```
[ Client ] 
   │  POST /api/content/ingest
   ▼
[ Spring Boot API ]
   │ 1) valida / persiste "PENDING"
   │ 2) manda mensaje a SQS (localstack)
   ▼
[ SQS Queue ]
   │
   ▼
[ Consumer/Worker Java ]
   │ 1) llama a Python Processor (HTTP)
   │ 2) actualiza estado en DB (IN_PROGRESS / READY / FAILED)
   │ 3) cachea resultado en Redis
   ▼
[ Postgres + Redis ]
   ▲
   │ GET /api/content/{id}
   │ lee de cache o DB
   ▼
[ Client ]
```

Primer día: Dejar andando un API REST + cache + observabilidad, sin SQS ni DB todavía