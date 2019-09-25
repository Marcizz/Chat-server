package chatserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements Runnable {
	protected List<String> userList = new ArrayList<String>();
	protected List<HandleClient> clientList = new ArrayList<HandleClient>();
	private ServerSocket serverSocket;
	private int serverPort;

	public ChatServer(int port) {
		this.serverPort = port;
	}

	/*
	 * The first line a client sends is used as as identifier and username in the chat.
	 */
	private void launchServer(int port) throws Exception {
		serverSocket = new ServerSocket(port);
		System.out.println("Server Started...");
		
		while (true) {
			Socket connectingClient = serverSocket.accept();
			HandleClient newUser = new HandleClient(connectingClient, this);
			clientList.add(newUser);
		}
		
	}

	protected void broadcast(String user, String message) {
		for (HandleClient client : clientList) {
			client.sendMessage(user, message);
		}
	}

	public void run() {
		try {
			launchServer(serverPort);
		} catch (Exception e) {
			System.err.println("Unable to start server: " + e.getLocalizedMessage());
		}
	}

	public void stopServer() {
		try {
			System.out.println("Server Shutdown...");
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error attempting to close server: " + e.getLocalizedMessage());
		}
	}
}