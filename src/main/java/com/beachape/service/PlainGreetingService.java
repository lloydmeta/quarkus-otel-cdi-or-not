package com.beachape.service;

import io.opentelemetry.instrumentation.annotations.WithSpan;

/// Same as [GreetingService] but produced **without** [io.quarkus.arc.InterceptionProxy],
/// so `@WithSpan` interception should *not* fire.
public class PlainGreetingService {

    @WithSpan
    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}
