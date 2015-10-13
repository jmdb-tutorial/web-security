package jmdbtutorial.websecurity.openam.resourceserver_a.api;

import com.codahale.metrics.annotation.Timed;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/protecteda")
@Produces(APPLICATION_JSON)
public class ProtectedResourceA {

    //@RolesAllowed("worker")
    @GET
    @Timed
    public SecretKnowledgeA tellMeTheSecret() {
        return new SecretKnowledgeA("The answer is..... 42");
    }
}