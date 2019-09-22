package chatserver.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HandleClient extends Thread {
	String userName = "";
	BufferedReader input;
	PrintWriter output;
	ChatServer server;

	public HandleClient(Socket client, ChatServer server) throws Exception {
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new PrintWriter(client.getOutputStream(), true);
		this.server = server;
		userName = "U" + System.currentTimeMillis();
		server.users.add(userName); // add to vector
		start();
	}

	public void sendMessage(String userName, String msg) {
		output.println(msg);
	}

	public String getUserName() {
		return userName;
	}

	public void run() {
		String line;
		try {
			while (true) {
				line = input.readLine();
				if (line.equals("quit")) {
					server.clients.remove(this);
					server.users.remove(userName);
					break;
				}
				if (line != null) {
					server.broadcast(userName, line);
				}

			}
		} catch (Exception ex) {
			System.err.println(">>> " + getClass() + " : " + ex.getMessage());
		}
	}
}