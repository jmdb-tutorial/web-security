package jmdbtutorial.websecurity.platform.dropwizard;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import jmdbtutorial.websecurity.platform.auth.User;
import jmdbtutorial.websecurity.platform.http.Http;
import jmdbtutorial.websecurity.platform.openam.OpenAmClient;

import java.security.Principal;

public class OpenamAuth implements Authenticator<String, Principal>, Authorizer<Principal> {

    private final OpenAmClient client = new OpenAmClient(new Http());



    @Override
    public Optional<Principal> authenticate(String credentials) throws AuthenticationException {
        return Optional.fromNullable(client.validateToken(credentials));
    }

    @Override
    public boolean authorize(Principal principal, String role) {
        if (!principal.getClass().isAssignableFrom(User.class)) {
            throw new RuntimeException("Something very bad has gone wrong here!");
        }

        return ((User)principal).isInRole(role);
    }
}
