package com.faustino.content_ingestion.service;

import com.faustino.content_ingestion.dto.ContentIngestionRequest;
import com.faustino.content_ingestion.dto.ContentResponse;
import com.faustino.content_ingestion.model.ContentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class ContentIngestionService {
    private static final Logger log = LoggerFactory.getLogger(ContentIngestionService.class);

    public ContentResponse ingest(ContentIngestionRequest request) {
        log.info("Received ingestion request id={} sourceUrl={}", request.id(), request.sourceUrl());
        // Placeholder: in the future enqueue message for async processing
        return new ContentResponse(
                request.id(),
                ContentStatus.PENDING,
                new HashMap<>(request.metadata()),
                Optional.empty());
    }
}
