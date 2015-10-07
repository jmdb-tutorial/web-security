package jmdbtutorial.websecurity.openam.resourceserver_a;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.openam.resourceserver_a.api.ProtectedResourceA;
import jmdbtutorial.websecurity.platform.DropwizardWebApp;

import static jmdbtutorial.websecurity.openam.platform.DropwizardCORSFilterConfig.addCrossOriginResourceSharingFilter;

public class ResourceServer_A_App extends DropwizardWebApp<ResourceServer_A_Configuration> {

    public static void main(String[] args) throws Exception {
        new ResourceServer_A_App("a.resource.server.com", "application", "/web_application/resource_a/public", "index.html", false).run(args);
    }

    public ResourceServer_A_App(String hostName, String rootPath, String serveFrom, String indexPage, boolean disableJersey) {
        super(hostName, rootPath, serveFrom, indexPage, disableJersey);
    }


    @Override
    public void run(ResourceServer_A_Configuration configuration,
                    Environment environment) {

        addCrossOriginResourceSharingFilter(environment, "http://websecurity.tutorial.com:8085");

        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new ProtectedResourceA());
        super.run(configuration, environment);
    }
}