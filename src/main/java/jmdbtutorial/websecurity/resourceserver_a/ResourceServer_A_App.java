package jmdbtutorial.websecurity.resourceserver_a;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.resourceserver_a.api.ProtectedResource;

public class ResourceServer_A_App extends Application<ResourceServer_A_Configuration> {

    public static void main(String[] args) throws Exception {
        new ResourceServer_A_App().run(args);
    }

    @Override
    public void initialize(Bootstrap<ResourceServer_A_Configuration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ResourceServer_A_Configuration configuration,
                    Environment environment) {
        environment.jersey().register(new ProtectedResource());
    }
}