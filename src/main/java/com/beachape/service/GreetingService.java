package com.beachape.service;

import io.opentelemetry.instrumentation.annotations.WithSpan;

/// A plain Java class (not a CDI bean) with a @WithSpan method.
/// Mimics UIAM's infra pattern: plain classes instantiated via `new` in CDI producers.
public class GreetingService {

    @WithSpan
    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}
