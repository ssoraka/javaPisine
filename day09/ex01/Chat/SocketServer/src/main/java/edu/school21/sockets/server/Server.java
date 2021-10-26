package edu.school21.sockets.server;

import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

@Component
public class Server {
    @Autowired
    private UsersService userService;
    @Autowired
    private MessageService messageService;

    public static LinkedList<ClientListener> serverList = new LinkedList<>();

    public void run(int port) {
        long id;
        try (ServerSocket server = new ServerSocket(port)) {
            id = 0L;
            System.out.println("Server start with port=" + port);
            while (true) {
                System.out.println(serverList.size() + " clients connected to the server");
                Socket socket = server.accept();
                try {
                    serverList.add(new ClientListener(socket, ++id, userService, messageService));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
