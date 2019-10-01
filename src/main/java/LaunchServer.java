import chatserver.server.ChatServer;

public class LaunchServer {
	public static void main(String[] args) {
		ChatServer server = new ChatServer(8080);
		new Thread(server).start();
	}

}
