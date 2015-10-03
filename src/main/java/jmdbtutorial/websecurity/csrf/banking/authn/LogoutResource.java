package jmdbtutorial.websecurity.csrf.banking.authn;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;

import static jmdbtutorial.websecurity.csrf.banking.authn.LoginResource.AUTH_COOKIE_NAME;

@Path("logout")
public class LogoutResource {

    @GET
    @Timed
    public Response logout(@QueryParam("redirect") String redirectUrl) {
        return Response.seeOther(URI.create(redirectUrl))
                .cookie(new NewCookie(AUTH_COOKIE_NAME, "loggedout", "/", "banking.example.com", "Used to login", 0, false))
                .build();
    }
}
