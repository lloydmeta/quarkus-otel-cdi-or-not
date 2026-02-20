package com.beachape;

import static org.assertj.core.api.Assertions.assertThat;

import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.List;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/// Reverse of [InterceptionProxySpanTest]: verifies that a service produced with
/// plain `new` (no [io.quarkus.arc.InterceptionProxy]) does **not** get `@WithSpan`
/// interception, so its span is missing from the trace.
@QuarkusTest
public class PlainProducerSpanTest {

    @Inject
    InMemorySpanExporter spanExporter;

    @BeforeEach
    void setUp() {
        spanExporter.reset();
    }

    @Test
    void greetingServiceSpanShouldNotAppearWithoutInterceptionProxy() {
        RestAssured.given()
                .when().get("/v1/plain-hello")
                .then().statusCode(200);

        Awaitility.await().atMost(Duration.ofSeconds(5)).pollDelay(Duration.ofSeconds(2)).untilAsserted(() -> {
            List<SpanData> spans = spanExporter.getFinishedSpanItems();

            System.out.println("=== Collected spans (plain producer, no InterceptionProxy) ===");
            for (SpanData span : spans) {
                System.out.printf("  span: name=%-40s traceId=%s parentSpanId=%s%n",
                        span.getName(), span.getTraceId(), span.getParentSpanId());
            }

            assertThat(spans)
                    .extracting(SpanData::getName)
                    .as("PlainGreetingService.greet span should NOT be present without InterceptionProxy")
                    .noneMatch(name -> name.contains("PlainGreetingService.greet"));
        });
    }
}
