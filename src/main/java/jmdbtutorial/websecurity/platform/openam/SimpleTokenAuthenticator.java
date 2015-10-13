package jmdbtutorial.websecurity.platform.openam;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;


public class SimpleTokenAuthenticator implements Authenticator<String, Principal> {

    Logger LOG = LoggerFactory.getLogger(SimpleTokenAuthenticator.class);

    @Override
    public Optional<Principal> authenticate(String credentials) throws AuthenticationException {
        LOG.info("Trying to authenticate " + credentials);
        return Optional.of(new User("foo"));
    }
}
