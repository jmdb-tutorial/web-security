package jmdbtutorial.websecurity.platform;

import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;
import io.dropwizard.Application;
import io.dropwizard.Bundle;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class DropwizardWebApp<T extends Configuration> extends Application<T> {

    Logger LOG = LoggerFactory.getLogger(DropwizardWebApp.class);
    private String hostName;
    private String rootPath;
    private final String serveFrom;
    private final String indexPage;
    private final boolean disableJersey;

    public DropwizardWebApp(String hostName, String rootPath, String serveFrom, String indexPage, boolean disableJersey) {
        this.hostName = hostName;
        this.rootPath = rootPath;
        this.serveFrom = serveFrom;
        this.indexPage = indexPage;
        this.disableJersey = disableJersey;
    }

    @Override
    public void initialize(Bootstrap<T> bootstrap) {
        bootstrap.addBundle((ConfiguredBundle) new ConfiguredAssetsBundle(serveFrom, "/", indexPage));
    }

    @Override
    public void run(T configuration, Environment environment) {

        if (disableJersey) {
            environment.jersey().disable();
        }

        environment.lifecycle().addServerLifecycleListener(new ServerLifecycleListener() {

            public void serverStarted(Server server) {
                for (Connector connector : server.getConnectors()) {
                    if (connector instanceof ServerConnector) {
                        ServerConnector serverConnector = (ServerConnector) connector;
                        System.out.println(serverConnector.getName() + " " + serverConnector.getLocalPort());
                        LOG.info("");
                        LOG.info(format("Started web application @ http://%s:%s/%s", hostName, serverConnector.getLocalPort(), rootPath));
                        LOG.info("");
                    }
                }
            }
        });

    }

}
