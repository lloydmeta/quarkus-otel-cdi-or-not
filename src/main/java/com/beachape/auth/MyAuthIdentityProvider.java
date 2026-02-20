package com.beachape.auth;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyAuthIdentityProvider implements IdentityProvider<MyAuthRequest> {

    @Override
    public Class<MyAuthRequest> getRequestType() {
        return MyAuthRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(MyAuthRequest request, AuthenticationRequestContext context) {
        return context.runBlocking(() -> QuarkusSecurityIdentity.builder()
                .setPrincipal(request::getUsername)
                .build());
    }
}
