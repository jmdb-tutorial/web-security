package jmdbtutorial.websecurity.resourceserver_b;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.resourceserver_b.api.ProtectedResourceB;

public class ResourceServer_B_App extends Application<ResourceServer_B_Configuration> {

    public static void main(String[] args) throws Exception {
        new ResourceServer_B_App().run(args);
    }

    @Override
    public void initialize(Bootstrap<ResourceServer_B_Configuration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ResourceServer_B_Configuration configuration,
                    Environment environment) {
        environment.jersey().register(new ProtectedResourceB());
    }
}