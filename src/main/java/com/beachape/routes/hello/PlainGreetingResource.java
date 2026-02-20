package com.beachape.routes.hello;

import com.beachape.service.PlainGreetingService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Default
@ApplicationScoped
@RunOnVirtualThread
public class PlainGreetingResource {

    @Inject
    PlainGreetingService plainGreetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return plainGreetingService.greet("World");
    }
}
