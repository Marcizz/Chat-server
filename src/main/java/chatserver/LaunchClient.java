package chatserver;

import chatserver.client.ChatClient;

public class LaunchClient {
	public static void main(String[] args) {
		ChatClient client = new ChatClient("127.0.0.1", 55555);
	}
}
