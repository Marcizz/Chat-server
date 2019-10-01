package chatclient;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import chatserver.client.ChatClient;
import chatserver.server.ChatServer;

/***
 * 
 * @author Marcus Laitala & Emil Albrektsson 2019-09-21
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientTest {

	private ChatClient client;
	private static ChatServer server;
	private final int portNumber = 44445;

	@Before
	public void setUp() throws IOException, InterruptedException {
		server = new ChatServer(portNumber);
		new Thread(server).start();
		client = new ChatClient(portNumber);
	}

	@AfterClass
	public static void StopOngoingProcesses() throws IOException {
		server.stopServer();		
	}

	@Test
	public void test1ClientConnectingToServer() {
		//Check if client is or was connected to the server
		Assert.assertTrue(client.getSocket().isClosed());
	}

	@Test(timeout = 1000)
	public void test2ConnectionToServerAndSendingAMessage() {
		String message = "Test";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));

			client.getServer().sendMessage(message);
			String actual = reader.readLine();
			Assert.assertTrue(message.contentEquals(actual));
			reader.close();

		} catch (IOException e) {
			fail(e.getLocalizedMessage() + "\n" + e.getStackTrace());
			client.stopClient();
		}
	}
}