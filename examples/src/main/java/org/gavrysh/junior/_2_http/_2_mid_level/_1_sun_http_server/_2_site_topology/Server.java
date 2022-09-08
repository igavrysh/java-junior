package org.gavrysh.junior._2_http._2_mid_level._1_sun_http_server._2_site_topology;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import sun.net.httpserver.DefaultHttpServerProvider;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws IOException {
        final int backlog = 64;
        final InetSocketAddress serverAddress = new InetSocketAddress(80);

        HttpServerProvider provider = DefaultHttpServerProvider.provider();
        HttpServer server = provider.createHttpServer(serverAddress, backlog);


        HttpContext baseContext = server.createContext("/");
        baseContext.setHandler(new PageHttpHandler("" +
                "<html>\n" +
                "   <body>\n" +
                "       <p><a href=\"/a.do\">a.do</a></p>\n" +
                "       <p><a href=\"/b.do\">b.do</a></p>\n" +
                "   </body>\n" +
                "</html>\n"
        ));

        HttpContext aContext = server.createContext("/a.do");
        aContext.setHandler(new PageHttpHandler("" +
                "<html>\n" +
                "   <body>\n" +
                "       <p><a href=\"/b.do\">b.do</a></p>\n" +
                "   </body>\n" +
                "</html>\n"
        ));

        HttpContext bContext = server.createContext("/b.do");
        bContext.setHandler(new PageHttpHandler("" +
                "<html>\n" +
                "   <body>\n" +
                "       <p><a href=\"/a.do\">a.do</a></p>\n" +
                "   </body>\n" +
                "</html>\n"
        ));

        server.start();
    }
}
