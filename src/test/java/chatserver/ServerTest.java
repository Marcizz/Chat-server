package chatserver;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import chatserver.server.ChatServer;

public class ServerTest {

	ChatServer server;
	String testServerIp = "127.0.0.1";
	int testServerPort = 27500;

	@Before
	public void setupServer() {

		new Thread(

				new Runnable() {

					public void run() {
						server = new ChatServer(testServerPort);
					}

				}

		).start();

	}

	@Test(timeout = 2000)
	public void testServerAcceptsConnections() {
		try {
			Socket client = new Socket(testServerIp, testServerPort);
			assertTrue(client.isConnected());
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(timeout = 2000)
	public void testServerReturnsMessagesToClient() {
		String testMsg = "abcdef";
		try {
			Socket client = new Socket(testServerIp, testServerPort);

			// Setup input/output stream
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);

			InputStream in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			// Send message
			writer.println(testMsg);
			String response = reader.readLine();
			
			assertTrue(testMsg.contentEquals(response));
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
