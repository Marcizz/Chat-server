import chatserver.client.*;
import chatserver.server.*;
public class main {	

	public static void main(String[] args) {
		ChatClient client;
		ChatServer server;		
		int port = 44447;
		server = new ChatServer(port);
		new Thread(server).start();
		client = new ChatClient(port);
	}
}
