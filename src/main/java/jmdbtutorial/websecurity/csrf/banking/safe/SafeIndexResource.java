package jmdbtutorial.websecurity.csrf.banking.safe;

import com.codahale.metrics.annotation.Timed;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.Response.ok;

/**
 * Remember to add '*.hbs' to intellij resource file types
 */
@Path("index.html")
@Produces(TEXT_HTML)
public class SafeIndexResource {

    public static final String CSRF_COOKIE_NAME = "csrfCookie";

    @GET
    @Timed
    @Produces("text/html")
    public Response get_index() {
        Handlebars handlebars = new Handlebars();

        try {
            Template template = handlebars.compile(getTemplateFilename("index"));

            String requestId = UUID.randomUUID().toString();

            return ok(template.apply(csrfContext(requestId)))
                    .cookie(new NewCookie(CSRF_COOKIE_NAME, requestId, "/", "banking.example.com", "Used to verify the request (usually this would be secure so you wouldnt see it in browser)", 100, false))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Problem producing response from template (See stack trace)", e);
        }

    }

    private Map<String, String> csrfContext(String requestId) {
        Map<String, String> context = new HashMap<>();

        context.put("csrfToken", requestId);

        return context;
    }


    private String getTemplateFilename(String templateName) {
        return this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/" + templateName;
    }
}
