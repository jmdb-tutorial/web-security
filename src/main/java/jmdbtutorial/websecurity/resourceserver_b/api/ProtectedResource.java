package jmdbtutorial.websecurity.resourceserver_b.api;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/protected")
@Produces(APPLICATION_JSON)
public class ProtectedResource {

    @GET
    @Timed
    public SecretKnowledge tellMeTheSecret() {
        return new SecretKnowledge("The question is..... How many streets must a mouse walk down?");
    }
}