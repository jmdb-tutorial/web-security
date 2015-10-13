package jmdbtutorial.websecurity.platform.dropwizard;

import io.dropwizard.auth.Authorizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

public class SimpleAuthorizer implements Authorizer<Principal> {

    Logger LOG = LoggerFactory.getLogger(SimpleAuthorizer.class);

    @Override
    public boolean authorize(Principal principal, String role) {
        LOG.info("authorizing principal " + principal + " for role " + role);
        return true;
    }
}
