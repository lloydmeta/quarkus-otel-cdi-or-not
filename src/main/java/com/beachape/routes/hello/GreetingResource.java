package com.beachape.routes.hello;

import com.beachape.service.GreetingService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Default
@ApplicationScoped
@RunOnVirtualThread
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return greetingService.greet("World");
    }

    @Authenticated
    @GET
    @Path("/say-my-name")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayMyName(@Context SecurityIdentity securityIdentity) {
        var principal = securityIdentity.getPrincipal();
        return principal != null ? greetingService.greet(principal.getName()) : greetingService.greet("anonymous");
    }
}
