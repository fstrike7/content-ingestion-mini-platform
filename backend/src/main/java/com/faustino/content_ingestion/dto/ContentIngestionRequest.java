package com.faustino.content_ingestion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Map;

public record ContentIngestionRequest(
        @Positive(message = "id must be positive")
        int id,
        @NotBlank(message = "sourceUrl is required")
        String sourceUrl,
        @NotNull @NotEmpty(message = "metadata is required")
        Map<
                @NotBlank(message = "metadata keys must be non-blank") String,
                @NotBlank(message = "metadata values must be non-blank") String> metadata
) {}
