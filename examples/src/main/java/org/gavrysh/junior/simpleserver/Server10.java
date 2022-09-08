package org.gavrysh.junior.simpleserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Server10 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(81);
        while (true) {
            System.out.println("wait ......");
            Socket socket = serverSocket.accept();
            System.out.println("get one!");
            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream();
                 PrintWriter printWriter = new PrintWriter(out, true)
            ) {
                byte[] request = HttpUtils.readRequestFully(in);
                System.out.println("-------");
                System.out.println(new String(request, StandardCharsets.ISO_8859_1));
                System.out.println("-------");

                // READ request
                //byte[] request = HttpUtils.createResponse("Hello World"); //HttpUtils.readFullRequest(in);
                //System.out.println(new String(request, "ASCII"));
                // WRITE response
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK" + "\r\n\r\n" + today;
                out.write(httpResponse.getBytes(StandardCharsets.ISO_8859_1));
            } finally {
                socket.close();
            }
        }

        /*
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            System.out.println("wait for TCP-connection ...");
            // TODO: what is happening on accept?
            // TODO: describe handshake procedure
            Socket socket = serverSocket.accept();
            System.out.println("get one!");
            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()
            ) {
                // READ request
                byte[] request = HttpUtils.readRequestFully(in);
                System.out.println("-------");
                System.out.println(new String(request, StandardCharsets.ISO_8859_1));
                System.out.println("-------");
                // WRITE request
                //byte[] response = new Date().toString().getBytes(StandardCharsets.UTF_8);

                byte[] response = "Hello".getBytes(StandardCharsets.UTF_8);

                out.write(response);
            } finally {
                socket.close();
            }
            // TODO: what happens on OS.close
        }

         */
    }
}