package jmdbtutorial.websecurity.csrf.hacker;

import io.dropwizard.setup.Environment;
import jmdbtutorial.websecurity.csrf.banking.BankingWebapp_Configuration;
import jmdbtutorial.websecurity.platform.DropwizardWebApp;

public class HackerWebapp extends DropwizardWebApp<HackerWebapp_Configuration> {

    public static void main(String[] args) throws Exception {
        new HackerWebapp("hacker.com", "application", "/web_application/csrf/hacker/public", "index.html").run(args);
    }

    public HackerWebapp(String hostName, String rootPath, String serveFrom, String indexPage) {
        super(hostName, rootPath, serveFrom, indexPage, true);
    }

    @Override
    public void run(HackerWebapp_Configuration configuration, Environment environment) {
        super.run(configuration, environment);
    }
}
