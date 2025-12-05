package com.faustino.content_ingestion.service;

import com.faustino.content_ingestion.dto.ContentResponse;
import com.faustino.content_ingestion.model.ContentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentQueryServiceTest {

    private final ContentQueryService service = new ContentQueryService();

    @Test
    void getContentByIdReturnsDummyResponse() {
        ContentResponse response = service.getContentById("10");

        assertThat(response.id()).isEqualTo(10);
        assertThat(response.status()).isEqualTo(ContentStatus.READY);
        assertThat(response.metadata()).containsEntry("example", "demo").containsEntry("type", "placeholder");
        assertThat(response.processedData()).contains(true);
    }

    @Test
    void getContentByIdRejectsInvalidIds() {
        assertThrows(ResponseStatusException.class, () -> service.getContentById("abc"));
    }
}
