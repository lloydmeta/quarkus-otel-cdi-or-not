package com.beachape.auth;

import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class MyAuthMechanism implements HttpAuthenticationMechanism {

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        String authorizationHeader = context.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            return Uni.createFrom().nullItem();
        }
        String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
        String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials));
        String[] parts = credentials.split(":", 2);
        if (parts.length != 2) {
            return Uni.createFrom().nullItem();
        }
        return identityProviderManager.authenticate(new MyAuthRequest(parts[0], parts[1]));
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        return Uni.createFrom().item(new ChallengeData(
                Response.Status.UNAUTHORIZED.getStatusCode(),
                HttpHeaders.WWW_AUTHENTICATE,
                "Basic realm=\"beachape\""));
    }
}
