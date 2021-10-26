package edu.school21.client.client;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    private PrintWriter printer;

    public Client() {
    }

    private void writeLine(String text) {
        printer.println(text);
        printer.flush();
    }

    public void run(String host, int port) {

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter print = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            printer = print;

            while (true) {
                String serverMessage = in.readLine();
                if (serverMessage.equals("Exit")) {
                    break;
                }
                System.out.println(serverMessage);
                System.out.print(">");
                String myAnswer = reader.readLine();
                writeLine(myAnswer);
            }

        } catch (ConnectException e) {
            System.out.println("The server is not available!");
        } catch (SocketException e) {
            System.out.println("Error connecting to the server!");
        } catch (Exception e) {
            ;
        } finally {
            System.out.println("You have left the chat.");
        }
    }
}
