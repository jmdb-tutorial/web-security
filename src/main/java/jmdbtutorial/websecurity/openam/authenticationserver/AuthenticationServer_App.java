package jmdbtutorial.websecurity.openam.authenticationserver;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.openam.authenticationserver.api.AuthenticateResource;

import static jmdbtutorial.websecurity.platform.dropwizard.DropwizardCORSFilterConfig.addCrossOriginResourceSharingFilter;

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


        addCrossOriginResourceSharingFilter(environment, "http://websecurity.tutorial.com:8085");

        environment.jersey().register(new AuthenticateResource());


    }

}
