package chatserver;

import chatserver.server.ChatServer;

public class LaunchServer {
	public static void main(String[] args) {
		ChatServer server = new ChatServer(55555);
		new Thread(server).start();
	}

}
