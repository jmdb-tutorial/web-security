package jmdbtutorial.websecurity.openam.platform;

import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;

import static javax.servlet.DispatcherType.REQUEST;
import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

public class DropwizardCORSFilterConfig {
    public static void addCrossOriginResourceSharingFilter(Environment environment, String origins) {
        FilterHolder filterHolder = environment.getApplicationContext().addFilter(CrossOriginFilter.class, "/*", EnumSet.of(REQUEST));

        filterHolder.setInitParameter(ACCESS_CONTROL_ALLOW_METHODS_HEADER, "GET,POST,DELETE,PUT,OPTIONS");
        filterHolder.setInitParameter(ALLOWED_ORIGINS_PARAM, origins);
        filterHolder.setInitParameter(ALLOWED_METHODS_PARAM, "GET,POST,DELETE,PUT,OPTIONS");
        filterHolder.setInitParameter(ALLOWED_HEADERS_PARAM, "X-Auth-Username, X-Auth-Password, X-Requested-With,Content-Type,Accept,Origin,Access-Control-Request-Headers,cache-control");


    }
}
