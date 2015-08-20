package jmdbtutorial.websecurity.web_application;

import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.resourceserver_b.ResourceServer_B_Configuration;
import jmdbtutorial.websecurity.resourceserver_b.api.ProtectedResourceB;

public class WebApplication extends Application<WebApplication_Configuration> {

    public static void main(String[] args) throws Exception {
        new WebApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<WebApplication_Configuration> bootstrap) {
        bootstrap.addBundle(new ConfiguredAssetsBundle("/web_application/public", "/", "index.html"));
    }

    @Override
    public void run(WebApplication_Configuration configuration,
                    Environment environment) {

        environment.jersey().disable();

    }
}