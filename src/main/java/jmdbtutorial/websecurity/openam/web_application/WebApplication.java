package jmdbtutorial.websecurity.openam.web_application;

import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class WebApplication extends Application<WebApplication_Configuration> {

    Logger LOG = LoggerFactory.getLogger(WebApplication.class);


    public static void main(String[] args) throws Exception {
        new WebApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<WebApplication_Configuration> bootstrap) {
        bootstrap.addBundle(new ConfiguredAssetsBundle("/web_application/csrf/bank/public", "/", "index.html"));
    }

    @Override
    public void run(WebApplication_Configuration configuration,
                    Environment environment) {

        environment.jersey().disable();

        environment.lifecycle().addServerLifecycleListener(new ServerLifecycleListener() {

            public void serverStarted(Server server) {
                for (Connector connector : server.getConnectors()) {
                    if (connector instanceof ServerConnector) {
                        ServerConnector serverConnector = (ServerConnector) connector;
                        System.out.println(serverConnector.getName() + " " + serverConnector.getLocalPort());
                        LOG.info("");
                        LOG.info(format("Started web application @ http://%s:%s/application", "websecurity.tutorial.com", serverConnector.getLocalPort()));
                        LOG.info("");
                    }
                }
            }
        });

    }


}