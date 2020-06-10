package server;

import org.eclipse.jetty.servlet.ServletContextHandler;

import java.awt.*;
import java.net.URI;


public class Server {
    private final org.eclipse.jetty.server.Server server;

    public Server(int port) {
        server = new org.eclipse.jetty.server.Server(port);
    }

    public void setup() {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(handler);
        handler.addServlet(SongServlet.class, "/songs");
    }

    public void start() throws Exception {
        server.start();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI("http://localhost:8080/songs"));
        }
    }


    public org.eclipse.jetty.server.Server getServer() {
        return server;
    }
}
