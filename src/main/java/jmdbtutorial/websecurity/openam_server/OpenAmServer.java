package jmdbtutorial.websecurity.openam_server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static java.lang.String.format;

public class OpenAmServer {

    private static final Logger log = LoggerFactory.getLogger(OpenAmServer.class);

    public static void main(String[] args) {
        new OpenAmServer("openam-server", 9009, args[0]).start();
    }

    private String serverName;
    private final int httpPort;
    private final String webrootDir;
    private Server server;


    public OpenAmServer(String serverName, int httpPort, String webrootDir) {
        this.serverName = serverName;
        this.httpPort = httpPort;
        this.webrootDir = webrootDir;
    }

    public void start()  {
        try {
            log.info(format("Starting Server [%s] on port %d", serverName, httpPort));

            this.server = new Server(httpPort);

            server.setHandler(rootHandler());

            server.start();

            log.info((format("Static content is from [%s]", new File(webrootDir).getCanonicalPath())));
            log.info(format("Server [%s] started @ http://localhost:%d", serverName, httpPort));

            server.join();
        } catch (Throwable t) {
            throw new RuntimeException(format("Could not start server [%s] (see cause)", serverName), t);
        }
    }

    private Handler rootHandler() {
        HandlerList handlerList = new HandlerList();

        Handler[] handlers = new Handler[]{resourceHandler()};

        handlerList.setHandlers(handlers);

        return handlerList;
    }

    private ResourceHandler resourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(webrootDir);

        return resourceHandler;
    }

}