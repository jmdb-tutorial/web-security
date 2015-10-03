package jmdbtutorial.websecurity.csrf.banking.unsafe;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.TEXT_HTML;

@Path("unsafe_transfer")
@Produces(TEXT_HTML)
public class UnsafeTransferResource {

    @GET
    @Timed
    public String get_transfer(@QueryParam("amount") String amount,
                               @QueryParam("from") String from,
                               @QueryParam("to") String to) {
        return transfer(amount, from, to);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Timed
    public String post_transfer(@FormParam("amount") String amount,
                                @FormParam("from") String from,
                                @FormParam("to")String to) {
        return transfer(amount, from, to);
    }

    private String transfer(String amount, String from, String to) {
        return String.format("<html><p>You just transferred <b>%s</b> from <b>%s</b> to <b>%s</b> </p></html>", amount, from, to);
    }
}
