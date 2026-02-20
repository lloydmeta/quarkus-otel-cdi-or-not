package com.beachape;

import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class InMemorySpanExporterProducer {

    private static final InMemorySpanExporter EXPORTER = InMemorySpanExporter.create();

    @Produces
    @Singleton
    InMemorySpanExporter inMemorySpanExporter() {
        return EXPORTER;
    }
}
