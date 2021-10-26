package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {
    @Autowired
    private UsersService service;
    private PrintWriter printer;

    public Server() {
    }

    private void writeLine(String text) {
        printer.println(text);
        printer.flush();
    }

    public void run(int port) {

        try (ServerSocket server = new ServerSocket(port);
             Socket socket = server.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter print = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))
        ){
            printer = print;

            writeLine("Hello from Server!");

            String command = in.readLine();
            System.out.println("command = " + command);
            if (!command.equals("signUp"))
            {
                writeLine("exit");
                System.err.println("Bad command");
                return;
            }
            writeLine("Enter username:");
            String login = in.readLine();
            System.out.println("login = " + login);

            writeLine("Enter password:");
            String password = in.readLine();
            System.out.println("password = " + password);

            String answer;
            if (!service.signUp(login, password)) {
                answer = "A user with this login is already registered!";
            } else {
                answer = "Successful!";
            }
            writeLine(answer);
            System.out.println("answer = " + answer);
            writeLine("exit");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Disconnect");
        }
    }
}
