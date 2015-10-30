package jmdbtutorial.websecurity.openam.resourceserver_a;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.openam.resourceserver_a.api.ProtectedResourceA;
import jmdbtutorial.websecurity.platform.dropwizard.*;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import static jmdbtutorial.websecurity.platform.dropwizard.DropwizardCORSFilterConfig.addCrossOriginResourceSharingFilter;

public class ResourceServer_A_App extends DropwizardWebApp<ResourceServer_A_Configuration> {

    public static void main(String[] args) throws Exception {
        new ResourceServer_A_App("a.resource.server.com", "application", "/web_application/resource_a/public", "index.html", false).run(args);
    }

    public ResourceServer_A_App(String hostName, String rootPath, String resourcePath, String indexPage, boolean disableJersey) {
        super(hostName, rootPath, resourcePath, indexPage, disableJersey);
    }

    /**
     * Auth from https://dropwizard.github.io/dropwizard/0.9.0-SNAPSHOT/docs/manual/auth.html
     */
    @Override
    public void run(ResourceServer_A_Configuration configuration,
                    Environment environment) {


        addCrossOriginResourceSharingFilter(environment, "http://websecurity.tutorial.com,http://websecurity.tutorial.com:8085,http://websecurity.tutorial.com:8080");

        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new ProtectedResourceA());


        OpenamAuth openamAuth = new OpenamAuth();

        AuthFilter authFilter = new AuthorizationTokenCredentialsAuthFilter.Builder<>()
                .setAuthenticator(openamAuth)
                .setAuthorizer(openamAuth)
                .setPrefix("Bearer")
                .buildAuthFilter();

        environment.jersey().register(authFilter);
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        super.run(configuration, environment);
    }
}