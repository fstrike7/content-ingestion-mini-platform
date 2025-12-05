package com.faustino.content_ingestion.controller;

import com.faustino.content_ingestion.dto.ContentIngestionRequest;
import com.faustino.content_ingestion.dto.ContentResponse;
import com.faustino.content_ingestion.service.ContentIngestionService;
import com.faustino.content_ingestion.service.ContentQueryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
@Validated
public class ContentController {

    private static final Logger log = LoggerFactory.getLogger(ContentController.class);

    private final ContentIngestionService ingestionService;
    private final ContentQueryService queryService;

    public ContentController(ContentIngestionService ingestionService, ContentQueryService queryService) {
        this.ingestionService = ingestionService;
        this.queryService = queryService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<ContentResponse> ingest(@Valid @RequestBody ContentIngestionRequest request) {
        log.info("Handling ingestion request for content id={}", request.id());
        ContentResponse response = ingestionService.ingest(request);
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentResponse> getById(@PathVariable String id) {
        ContentResponse response = queryService.getContentById(id);
        return ResponseEntity.ok(response);
    }
}
