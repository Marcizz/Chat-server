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

	public void run() {
		try {
			launchServer();
		} catch (Exception e) {
			System.err.println("Unable to start server: " + e.getLocalizedMessage());
		}
	}

	private void launchServer() throws Exception {
		serverSocket = new ServerSocket(serverPort);
		System.out.println("Server running on port " + serverPort + "...");

		while (true) {
			Socket connectingClient = serverSocket.accept();
			HandleClient newUser = new HandleClient(connectingClient, this);
			clientList.add(newUser);
			System.out.println("New connection from " + connectingClient.getRemoteSocketAddress());
		}

	}

	protected void broadcast(String user, String message) {
		for (HandleClient client : clientList) {
			client.sendMessage(user, message);
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