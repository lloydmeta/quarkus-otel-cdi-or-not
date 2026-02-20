package com.beachape.service;

import io.quarkus.arc.InterceptionProxy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class GreetingServiceProducer {

    @Produces
    @ApplicationScoped
    GreetingService greetingService(InterceptionProxy<GreetingService> proxy) {
        return proxy.create(new GreetingService());
    }
}
