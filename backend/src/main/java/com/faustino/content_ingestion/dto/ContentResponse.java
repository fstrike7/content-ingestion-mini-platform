package com.faustino.content_ingestion.dto;

import com.faustino.content_ingestion.model.ContentStatus;

import java.util.Map;
import java.util.Optional;

public record ContentResponse(int id, ContentStatus status, Map<String, String> metadata, Optional<Boolean> processedData) {}
