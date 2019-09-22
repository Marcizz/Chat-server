package chatserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements Runnable {
	protected List<String> users = new ArrayList<String>();
	protected List<HandleClient> clients = new ArrayList<HandleClient>();
	ServerSocket server;
	private int port;

	public ChatServer(int port) {
		this.port = port;
	}

	private void process(int port) throws Exception {
		server = new ServerSocket(port);
		System.out.println("Server Started...");
		while (true) {
			Socket client = server.accept();
			HandleClient newUser = new HandleClient(client, this);
			clients.add(newUser);
		}
	}

	protected void broadcast(String user, String message) {
		for (HandleClient client : clients) {
			client.sendMessage(user, message);
		}
	}

	public void run() {
		try {
			process(port);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stopServer();
		}

	}

	public void stopServer() {
		try {
			server.close();
		} catch (IOException e) {
			System.err.println("Error attempting to close server: " + e.getLocalizedMessage());
		}
	}

}