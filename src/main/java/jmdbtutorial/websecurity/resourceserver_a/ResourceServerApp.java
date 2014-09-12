package jmdbtutorial.websecurity.resourceserver_a;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.resourceserver_a.api.ProtectedResource;

public class ResourceServerApp extends Application<ResourceServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new ResourceServerApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<ResourceServerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ResourceServerConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(new ProtectedResource());
    }
}