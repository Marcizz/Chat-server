package chatserver.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class HandleClient extends Thread {
	private String userName = "";
	private BufferedReader input;
	private PrintWriter output;
	private ChatServer server;

	public HandleClient(Socket client, ChatServer server) throws Exception {
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new PrintWriter(client.getOutputStream(), true);
		this.server = server;
		this.userName = input.readLine();
		server.userList.add(userName);
		sendSystemMessage(userName + " has entered the channel.");
		this.start();
	}

	public void sendMessage(String userName, String msg) {
		output.println("<" + userName + "> " + msg);
	}

	public void sendSystemMessage(String msg) {
		output.println(msg);
	}

	public void run() {
		String line;
		try {

			while (true) {
				line = input.readLine();
				TimeUnit.MILLISECONDS.sleep(20);
				
				if (line != null) {
					if (line.contains("exit")) {
						server.userList.remove(userName);
						server.clientList.remove(this);
						break;
					} else if (!line.isEmpty()) {
						server.broadcast(userName, line);
					}
				}
			}
		} catch (Exception ex) {
			System.err.println("ERROR >>> " + getClass() + " : " + ex.getLocalizedMessage());
			ex.printStackTrace();
		} finally {
			System.out.println("Disconnected from server.");
			sendSystemMessage(userName + " has left the server.");
		}

	}
}