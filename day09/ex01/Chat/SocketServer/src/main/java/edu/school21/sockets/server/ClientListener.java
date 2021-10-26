package edu.school21.sockets.server;

import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import java.io.*;
import java.net.Socket;

public class ClientListener extends Thread {
	private final String STOP = "Exit";
	private final Socket socket;
	private final Long id;
	private final BufferedReader in;
	private final BufferedWriter out;
	private String login;
	private String password;
	private UsersService userService;
	private MessageService messageService;

	public ClientListener(Socket socket, Long id, UsersService usersService, MessageService messageService) throws IOException,IllegalArgumentException {
		String command;
		this.socket = socket;
		this.id = id;
		this.userService = usersService;
		this.messageService = messageService;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		send("Hello from Server!");
		command = in.readLine();
		System.out.println("Client " + id + " set command = \"" + command + "\"");
		if(!command.equals("signIn")) {
			errorExit("Illegal comamnd!");
		}

		send("Enter username:");
		login = in.readLine();
		System.out.println("Client " + id + " set login = \"" + login + "\"");
		for(ClientListener cl : Server.serverList)
		{
			if (cl.login.equals(login)) {
				errorExit("The user \""+ login +"\" has already sign in!");
			}
		}
		if (!userService.signIn(login))
			errorExit("Not found login!");
		send("Enter password:");
		password = in.readLine();
		System.out.println("Client " + id + " set password");
		if (!userService.signIn(login, password))
			errorExit("Incorrect password!");

		send("Start messaging");

		System.out.println("Add new client " + id + " - \"" + login + "\"");
		start();
	}

	@Override
	public void run() {
		String clientMesaage;
		try {
			while (true) {
				clientMesaage = in.readLine();
				if(clientMesaage.equals(STOP)) {
					exitFromServer();
					break;
				}
				System.out.println("Client " + id + " - \"" + login + "\" say = \"" + clientMesaage + "\"");
				messageService.saveMessage(login, clientMesaage);
				for (ClientListener cl : Server.serverList) {
					cl.send(login + ": " + clientMesaage);
				}
			}
		} catch (IOException e) {
		}
	}

	private void send(String msg) {
		try {
			out.write(msg + "\n");
			out.flush();
		} catch (IOException ignored) {}
	}

	private void exitFromServer() {
		try {
			if(!socket.isClosed()) {
				send(STOP);
				System.out.println("Client " + id + " - \"" + login + "\" was sign out!");
				socket.close();
				in.close();
				out.close();
				Server.serverList.remove(this);
			}
		} catch (IOException ignored) {}
	}

	private void errorExit(String errMessage) {
		try {
			if(!socket.isClosed()) {
				System.out.println(errMessage);
				send(STOP);
				socket.close();
				in.close();
				out.close();
				throw new IllegalArgumentException("Client " + id + " was not added!");
			}
		} catch (IOException ignored) {}
	}
}
