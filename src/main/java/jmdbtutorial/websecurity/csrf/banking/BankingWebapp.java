package jmdbtutorial.websecurity.csrf.banking;

import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.csrf.banking.unsafe.UnsafeTransferResource;
import jmdbtutorial.websecurity.platform.DropwizardWebApp;

public class BankingWebapp extends DropwizardWebApp<BankingWebapp_Configuration> {


    public static void main(String[] args) throws Exception {
        new BankingWebapp("banking.example.com", "application", "/web_application/csrf/public", "index.html").run(args);
    }

    public BankingWebapp(String hostName, String rootPath, String serveFrom, String indexPage) {
        super(hostName, rootPath, serveFrom, indexPage, false);
    }

    @Override
    public void run(BankingWebapp_Configuration configuration, Environment environment) {
        environment.jersey().setUrlPattern("/banking/*");
        environment.jersey().register(new LoginResource());
        environment.jersey().register(new LogoutResource());
        environment.jersey().register(new UnsafeTransferResource());

        super.run(configuration, environment);
    }
}
