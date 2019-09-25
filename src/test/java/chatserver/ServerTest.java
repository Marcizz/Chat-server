package chatserver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import chatserver.server.ChatServer;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerTest {

	static ChatServer server;
	String testServerIp = "127.0.0.1";
	static int testServerPort = 27510;

	@BeforeClass
	public static void startServer() {
		server = new ChatServer(testServerPort);
		//new Thread(server).start();
	}

	@AfterClass
	public static void cleanupServer() {
		server.stopServer();
	}

	@Test(timeout = 1000)
	public void test1ServerAcceptsConnections() {
		try {
			Socket client = new Socket(testServerIp, testServerPort);
			assertFalse(client.isClosed());
			client.close();
		} catch (UnknownHostException e) {
			fail(e.getLocalizedMessage());
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test(timeout = 1000)
	public void test2ServerReturnsMessagesToClient() {
		String testMsg = "abcdef";
		try {
			Socket client = new Socket(testServerIp, testServerPort);

			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);

			InputStream in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			writer.println(testMsg);
			String response = reader.readLine();

			assertTrue(response.contains(testMsg));
			client.close();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test(timeout = 1000)
	public void test3ServerSendsMessageBetweenClients() {
		String testMsg = "testMessageToAnotherClient";
		try {
			
			// Setup clients & streams: client1 send, client2 receive
			Socket client1 = new Socket(testServerIp, testServerPort);
			OutputStream out = client1.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);

			Socket client2 = new Socket(testServerIp, testServerPort);
			InputStream in = client2.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			// Send message to server with client1
			writer.println(testMsg);

			// Check response from client2
			String response = reader.readLine();

			System.out.println("testMsg:" + testMsg + " response:" + response);

			assertTrue(response.contains(testMsg));

			client1.close();
			client2.close();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}

}
