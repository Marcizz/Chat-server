package chatserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	private ServerSocket server;

	public ChatServer(int port) {
		try {
			System.out.println("Waiting for client on port " + port);
			server = new ServerSocket(port);

			Socket socket = server.accept();

			server.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
		}
	}

	public ServerSocket getSocket() {
		return server;
	}

}
