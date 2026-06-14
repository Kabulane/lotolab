package com.lotolab.persistence;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MongoConnectivityIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void connectsToMongoDb() throws InterruptedException {
        RuntimeException lastFailure = null;

        for (int attempt = 0; attempt < 10; attempt++) {
            try {
                Document result = mongoTemplate.executeCommand(new Document("ping", 1));
                assertThat(result.getDouble("ok")).isEqualTo(1.0);
                return;
            } catch (RuntimeException exception) {
                lastFailure = exception;
                Thread.sleep(1_000);
            }
        }

        throw lastFailure;
    }
}
