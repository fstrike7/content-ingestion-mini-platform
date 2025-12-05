package com.faustino.content_ingestion.controller;

import com.faustino.content_ingestion.dto.ContentResponse;
import com.faustino.content_ingestion.model.ContentStatus;
import com.faustino.content_ingestion.service.ContentIngestionService;
import com.faustino.content_ingestion.service.ContentQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceInitializationAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.data.redis.autoconfigure.DataRedisRepositoriesAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = ContentController.class,
        excludeAutoConfiguration = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                DataSourceInitializationAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                DataRedisAutoConfiguration.class,
                DataRedisRepositoriesAutoConfiguration.class
        }
)
@Import(ContentControllerTest.TestConfig.class)
@ActiveProfiles("test")
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContentIngestionService ingestionService;

    @MockitoBean
    private ContentQueryService queryService;

    @Test
    void ingestReturnsAcceptedResponse() throws Exception {
        ContentResponse response = new ContentResponse(1, ContentStatus.PENDING, Map.of("kind", "video"), Optional.empty());
        given(ingestionService.ingest(any())).willReturn(response);

        mockMvc.perform(post("/api/content/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "sourceUrl": "http://example.com/video",
                                  "metadata": {"kind": "video"}
                                }
                                """))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.id").value(1));

        Mockito.verify(ingestionService).ingest(any());
    }

    @Test
    void ingestValidatesPayload() throws Exception {
        mockMvc.perform(post("/api/content/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 0,
                                  "sourceUrl": "",
                                  "metadata": []
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByIdReturnsContent() throws Exception {
        ContentResponse response = new ContentResponse(42, ContentStatus.READY, Map.of("example", "demo"), Optional.of(Boolean.TRUE));
        given(queryService.getContentById(eq("42"))).willReturn(response);

        mockMvc.perform(get("/api/content/42"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(42))
                .andExpect(jsonPath("$.status").value("READY"));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager();
        }
    }
}
