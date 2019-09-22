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
import org.junit.Test;

import chatserver.server.ChatServer;

public class ServerTest {

	private static ChatServer server;
	String testServerIp = "127.0.0.1";
	private static int testServerPort = 27510;

	@BeforeClass
	public static void startServer() {
		server = new ChatServer(testServerPort);
		new Thread(server).start();
	}
	
	@AfterClass
	public static void stopServer() {
		server.stopServer();
	}

	@Test(timeout = 2000)
	public void testServerAcceptsConnections() {
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

	@Test(timeout = 5000)
	public void testServerReturnsMessagesToClient() {
		String testMsg = "abcdef";
		try {
			Socket client = new Socket(testServerIp, testServerPort);

			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);

			InputStream in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			writer.println(testMsg);
			String response = reader.readLine();

			assertTrue(testMsg.contentEquals(response));
			client.close();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test(timeout = 2000)
	public void testServerSendsMessageBetweenClients() {
		String testMsg = "message from first client";
		try {
			Socket client1 = new Socket(testServerIp, testServerPort);
			Socket client2 = new Socket(testServerIp, testServerPort);

			// Setup streams: client1 send, client2 receive
			OutputStream out = client1.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);

			InputStream in = client2.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			// Send message to server with client1
			writer.println(testMsg);

			// Check response from client2
			String response = reader.readLine();

			assertTrue(testMsg.contentEquals(response));
			client1.close();
			client2.close();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}
}
