package chatserver;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import chatserver.server.ChatServer;

public class ServerTest {

	ChatServer server;

	@Before
	public void setupServer() {

			new Thread(
					
					new Runnable() {

						public void run() {
							server = new ChatServer(27500);				
						}
						
					}
					
			).start();
		
	}

	@Test(timeout = 2000)
	public void testServerAcceptsConnections() {
		try {
			System.out.println("Trying connection in: testServerAcceptsConnections()");
			Socket client = new Socket("127.0.0.1", 27500);
			assertNotNull(client);
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
