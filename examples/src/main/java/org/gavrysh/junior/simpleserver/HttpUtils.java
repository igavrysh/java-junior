package org.gavrysh.junior.simpleserver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpUtils {

    public static boolean isRequestEnd(byte[] request, int length) {
        if (length < 4) {
            return false;
        }
        return request[length-4] == '\r'
                && request[length-3] == '\n'
                && request[length-2] == '\r'
                && request[length-1] == '\n';
    }

    public static byte[] readFullRequest(InputStream in) throws IOException {
        byte[] buff = new byte[1024];
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        while (true) {
            int count = in.read(buff);
            result.write(buff, 0, count);
            if (isRequestEnd(buff, count)) {
                return result.toByteArray();
            }
        }
    }

    public static byte[] readRequestFully(InputStream in) throws IOException {
        byte[] buff = new byte[8192];
        int headerLen = 0;
        while (true) {
            int count = in.read(buff, headerLen, buff.length - headerLen);
            if (count < 0) {
                throw new RuntimeException("Incoming connection closed");
            } else {
                headerLen += count;
                if (isRequestEnd(buff, headerLen)) {
                    return Arrays.copyOfRange(buff, 0, headerLen);
                }
                if (headerLen == buff.length) {
                    throw new RuntimeException("Too big HTTP header");
                }
            }
        }
    }

    public static byte[] createResponse(String text) throws IOException {
        String html = ""
                + "<html>"
                + "<body>"
                + "<p style=\"color: #0000FF; font-size: 48pt\">"
                + text
                + "</p>"
                + "</body>"
                + "</html>";

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(result, StandardCharsets.ISO_8859_1);
        // http header
        writer.write("HTTP/1.1 200 OK\r\n");
        writer.write("Content-Type: text/html; charset=ISO-8859-1\r\n");
        writer.write("Content-Language: en\r\n");
        writer.write("Content-Length: " + html.length() + "\r\n");
        writer.write("Connection: close\r\n");
        writer.write("\r\n");
        // html
        writer.write(html);
        writer.flush();

        return result.toByteArray();
    }
}
