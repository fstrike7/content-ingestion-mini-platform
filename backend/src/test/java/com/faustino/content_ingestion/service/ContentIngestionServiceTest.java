package com.faustino.content_ingestion.service;

import com.faustino.content_ingestion.dto.ContentIngestionRequest;
import com.faustino.content_ingestion.dto.ContentResponse;
import com.faustino.content_ingestion.model.ContentStatus;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ContentIngestionServiceTest {

    private final ContentIngestionService service = new ContentIngestionService();

    @Test
    void ingestReturnsPendingResponse() {
        ContentIngestionRequest request = new ContentIngestionRequest(123, "http://example.com", Map.of("kind", "video"));

        ContentResponse response = service.ingest(request);

        assertThat(response.status()).isEqualTo(ContentStatus.PENDING);
        assertThat(response.id()).isEqualTo(request.id());
        assertThat(response.metadata()).containsExactlyEntriesOf(request.metadata());
        assertThat(response.processedData()).isEmpty();
    }
}
