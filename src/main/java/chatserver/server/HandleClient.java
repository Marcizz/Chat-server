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
		userName = "user" + server.users.size();
		server.users.add(userName);
		this.start();
	}

	public void sendMessage(String userName, String msg) {
		output.println(msg);
	}

	public void run() {
		String line;
		try {

			while (true) {
				line = input.readLine();
				sleep(20);
				if (line != null) {
					server.broadcast(userName, line);
				}
			}

		} catch (Exception ex) {
			System.err.println("ERROR >>> " + getClass() + " : " + ex.getLocalizedMessage());
		}
	}
}