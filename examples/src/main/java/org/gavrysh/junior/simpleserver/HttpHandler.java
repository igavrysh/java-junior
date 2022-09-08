package org.gavrysh.junior.simpleserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.Callable;

public class HttpHandler implements Callable<Void> {
    private final Socket socket;

    public HttpHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Void call() throws IOException {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()
        ) {
            // READ request
            byte[] request = HttpUtils.readFullRequest(in);
            System.out.println(new String(request, StandardCharsets.ISO_8859_1));
            // WRITE response
            byte[] response = HttpUtils.createResponse(new Date().toString());
            System.out.println(new String(response, StandardCharsets.ISO_8859_1));
            out.write(response);
        } finally {
            socket.close();
        }
        return null;
    }
}