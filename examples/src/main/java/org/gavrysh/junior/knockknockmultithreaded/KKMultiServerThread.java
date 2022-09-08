/**
 * Source: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 */

package org.gavrysh.junior.knockknockmultithreaded;

import org.gavrysh.junior.knockknock.KnockKnockProtocol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class KKMultiServerThread extends Thread {
    private Socket socket = null;

    public KKMultiServerThread(Socket socket) {
        super("KKMultiServerThread");
        this.socket = socket;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
