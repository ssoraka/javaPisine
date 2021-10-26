package edu.school21.sockets.client;


import java.io.*;
import java.net.Socket;

public class Client {
    private PrintWriter printer;

    public Client() {
    }

    private void writeLine(String text) {
        printer.println(text);
        printer.flush();
    }

    public void run(String host, int port) {

        boolean hasConnect = false;
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter print = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            printer = print;
            hasConnect = true;

            while (true) {
                String serverMessage = in.readLine();
                if (serverMessage.equals("exit")) {
                    System.out.println("Server is close");
                    System.exit(0);
                } else if (serverMessage.equals("Successful!") || serverMessage.equals("A user with this login is already registered!")) {
                    System.out.println(serverMessage);
                    System.exit(0);
                } else {
                    System.out.println(serverMessage);
                    System.out.print(">");
                    String myAnswer = reader.readLine();
                    writeLine(myAnswer);
                }
            }
        } catch (Exception e) {
            System.err.println("The server is not available!");
        } finally {
            if (hasConnect) {
                System.out.println("Disconnect");
            }
        }
    }
}
