package jmdbtutorial.websecurity.csrf.banking.unsafe;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_HTML_TYPE;
import static jmdbtutorial.websecurity.csrf.banking.authn.LoginResource.AUTH_COOKIE_NAME;
import static jmdbtutorial.websecurity.csrf.banking.authn.LoginResource.AUTH_SECRET;

@Path("unsafe_transfer")
@Produces(TEXT_HTML)
public class UnsafeTransferResource {

    @GET
    @Timed
    public Response get_transfer(@QueryParam("amount") String amount,
                               @QueryParam("from") String from,
                               @QueryParam("to") String to,
                               @CookieParam(AUTH_COOKIE_NAME) Cookie cookie) {

        return transfer(cookie, amount, from, to);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Timed
    public Response post_transfer(@FormParam("amount") String amount,
                                @FormParam("from") String from,
                                @FormParam("to")String to,
                                @CookieParam(AUTH_COOKIE_NAME) Cookie cookie) {

        return transfer(cookie, amount, from, to);
    }

    private Response transfer(Cookie cookie, String amount, String from, String to) {
        if (cookie == null || !AUTH_SECRET.equals(cookie.getValue())) {
            return Response.status(Response.Status.FORBIDDEN).entity("<html><h1>403 - ACCESS DENIED!</h1>").build();
        }
        return Response.ok(String.format("<html><p>You just transferred <b>%s</b> from <b>%s</b> to <b>%s</b> </p></html>", amount, from, to), TEXT_HTML_TYPE).build();
    }
}
