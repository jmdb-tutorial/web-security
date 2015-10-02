package jmdbtutorial.websecurity.authenticationserver.api;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/authenticate")
@Produces(APPLICATION_JSON)
public class AuthenticateResource {

    @POST
    @Timed
    public String authenticate() {
        return "hello";
    }

}
