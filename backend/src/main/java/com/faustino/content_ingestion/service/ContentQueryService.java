package com.faustino.content_ingestion.service;

import com.faustino.content_ingestion.dto.ContentResponse;
import com.faustino.content_ingestion.model.ContentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
public class ContentQueryService {

    private static final Logger log = LoggerFactory.getLogger(ContentQueryService.class);

    @Cacheable("contentById")
    public ContentResponse getContentById(String id) {
        int parsedId = parseId(id);
        log.info("Returning cached/dummy response for content id={}", parsedId);
        return new ContentResponse(parsedId, ContentStatus.READY, Map.of("example", "demo", "type", "placeholder"), Optional.of(Boolean.TRUE));
    }

    private int parseId(String rawId) {
        try {
            return Integer.parseInt(rawId);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid content id");
        }
    }
}
