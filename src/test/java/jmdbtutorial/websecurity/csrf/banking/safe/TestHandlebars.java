package jmdbtutorial.websecurity.csrf.banking.safe;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class TestHandlebars {

    @Test
    public void simple_template() throws Exception {
        Handlebars handlebars = new Handlebars();

        Template template = handlebars.compileInline("Hello {{this}}!");

        out.println(template.apply("Handlebars.java"));

    }

    @Test
    public void with_map() throws Exception {
        Handlebars handlebars = new Handlebars();

        Template template = handlebars.compileInline("Hello {{someParameter}}!");

        Map<String, String> context = new HashMap<>();
        context.put("someParameter", "FOO");
        out.println(template.apply(context));
    }
}
