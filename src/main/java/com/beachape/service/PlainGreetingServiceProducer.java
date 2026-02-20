package com.beachape.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

/// Produces [PlainGreetingService] via plain `new` — no [io.quarkus.arc.InterceptionProxy].
/// This means `@WithSpan` on [PlainGreetingService.greet] will **not** be intercepted by CDI.
@ApplicationScoped
public class PlainGreetingServiceProducer {

    @Produces
    @ApplicationScoped
    PlainGreetingService plainGreetingService() {
        return new PlainGreetingService();
    }
}
