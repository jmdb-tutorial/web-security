package jmdbtutorial.websecurity.csrf.banking;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("login")
public class LoginResource {

    public static final String AUTH_COOKIE_NAME = "authCookie";
    public static final String AUTH_SECRET= "$$secretToken$$";

    @Context
    UriInfo uriInfo;


    @GET
    @Timed
    public Response login(@QueryParam("redirect") String redirectUrl) {
        return Response.seeOther(URI.create(redirectUrl))
                .cookie(new NewCookie(AUTH_COOKIE_NAME, AUTH_SECRET, "/", "banking.example.com", "Used to login (usually this would be secure so you wouldnt see it in browser)", 100, false))
                .build();
    }


}
