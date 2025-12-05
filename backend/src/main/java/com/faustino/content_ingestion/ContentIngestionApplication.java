package com.faustino.content_ingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ContentIngestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentIngestionApplication.class, args);
	}

}
