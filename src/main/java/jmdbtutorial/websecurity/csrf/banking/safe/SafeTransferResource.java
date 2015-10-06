package jmdbtutorial.websecurity.csrf.banking.safe;

import com.codahale.metrics.annotation.Timed;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_HTML_TYPE;
import static jmdbtutorial.websecurity.csrf.banking.authn.LoginResource.AUTH_COOKIE_NAME;
import static jmdbtutorial.websecurity.csrf.banking.authn.LoginResource.AUTH_SECRET;
import static jmdbtutorial.websecurity.csrf.banking.safe.SafeIndexResource.CSRF_COOKIE_NAME;

@Path("safe_transfer")
@Produces(TEXT_HTML)
public class SafeTransferResource {

    @GET
    @Timed
    public Response get_transfer(@QueryParam("amount") String amount,
                                 @QueryParam("from") String from,
                                 @QueryParam("to") String to,
                                 @QueryParam("csrfToken") String csrfParam,
                                 @CookieParam(AUTH_COOKIE_NAME) Cookie authCookie,
                                 @CookieParam(CSRF_COOKIE_NAME) Cookie csrfCookie,
                                 @Context HttpServletRequest request) {

        return transfer(authCookie, csrfParam, csrfCookie, amount, from, to, request);
    }


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Timed
    public Response post_transfer(@FormParam("amount") String amount,
                                  @FormParam("from") String from,
                                  @FormParam("to")String to,
                                  @FormParam("csrfToken") String csrfParam,
                                  @CookieParam(AUTH_COOKIE_NAME) Cookie authCookie,
                                  @CookieParam(CSRF_COOKIE_NAME) Cookie csrfCookie,
                                  @Context HttpServletRequest request) {

        return transfer(authCookie, csrfParam, csrfCookie, amount, from, to, request);
    }

    private Response transfer(Cookie authCookie, String csrfParam, Cookie csrfCookie, String amount, String from, String to, HttpServletRequest request) {
        if (authCookie == null || !AUTH_SECRET.equals(authCookie.getValue())) {
            return Response.status(Response.Status.FORBIDDEN).entity("<html><h1>403 - ACCESS DENIED!</h1>").build();
        }

        if (csrfParam == null || csrfCookie == null
                || !csrfParam.equals(csrfCookie.getValue())) {
            String ipAddress = getIpAddressFrom(request);
            return Response.status(Response.Status.FORBIDDEN).entity("<html><h1>403 - ACCESS DENIED!</h1> <p>Naughty requestor, looks like you are attempting a cross site forgery attack, your IP address [" + ipAddress + "] has been logged and we will hunt you down!</p>").build();
        }

        return Response.ok(format("<html><p>You just transferred <b>%s</b> from <b>%s</b> to <b>%s</b> </p></html>", amount, from, to), TEXT_HTML_TYPE).build();
    }

    private String getIpAddressFrom(HttpServletRequest request) {
        String remoteHost = request.getRemoteHost();
        String remoteAddr = request.getRemoteAddr();
        int remotePort = request.getRemotePort();
        return format("%s (%s:%s)", remoteHost, remoteAddr, remotePort);
    }
}
