package jmdbtutorial.websecurity.authenticationserver;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.authenticationserver.api.AuthenticateResource;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;

import static javax.servlet.DispatcherType.REQUEST;
import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

public class AuthenticationServer_App extends Application<AuthenticationServer_Configuration> {

    public static void main(String[] args) throws Exception {
        new AuthenticationServer_App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AuthenticationServer_Configuration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(AuthenticationServer_Configuration configuration,
                    Environment environment) {


        addCrossOriginFilter(environment);

        environment.jersey().register(new AuthenticateResource());


    }

    private void addCrossOriginFilter(Environment environment) {
        FilterHolder filterHolder = environment.getApplicationContext().addFilter(CrossOriginFilter.class, "/*", EnumSet.of(REQUEST));

        filterHolder.setInitParameter(ACCESS_CONTROL_ALLOW_METHODS_HEADER, "GET,POST,DELETE,PUT,OPTIONS");
        filterHolder.setInitParameter(ALLOWED_ORIGINS_PARAM, "http://websecurity.tutorial.com:8085");
        filterHolder.setInitParameter(ALLOWED_METHODS_PARAM, "GET,POST,DELETE,PUT,OPTIONS");
        filterHolder.setInitParameter(ALLOWED_HEADERS_PARAM, "X-Auth-Username, X-Auth-Password, X-Requested-With,Content-Type,Accept,Origin,Access-Control-Request-Headers,cache-control");


    }
}
