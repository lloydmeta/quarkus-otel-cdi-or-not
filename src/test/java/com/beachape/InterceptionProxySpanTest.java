package com.beachape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class InterceptionProxySpanTest {

    @Inject
    InMemorySpanExporter spanExporter;

    @BeforeEach
    void setUp() {
        spanExporter.reset();
    }

    @Test
    void greetingServiceSpanShouldAppearWithInterceptionProxy() {
        RestAssured.given()
                .when().get("/v1/hello")
                .then().statusCode(200);

        await().atMost(Duration.ofSeconds(5)).untilAsserted(() -> {
            List<SpanData> spans = spanExporter.getFinishedSpanItems();

            System.out.println("=== Collected spans ===");
            for (SpanData span : spans) {
                System.out.printf("  span: name=%-40s traceId=%s parentSpanId=%s%n",
                        span.getName(), span.getTraceId(), span.getParentSpanId());
            }

            assertThat(spans)
                    .extracting(SpanData::getName)
                    .as("GreetingService.greet span should be present thanks to InterceptionProxy")
                    .anyMatch(name -> name.contains("GreetingService.greet"));
        });
    }
}
