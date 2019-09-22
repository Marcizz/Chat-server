package chatclient;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import chatserver.client.Client;
import chatserver.server.ChatServer;

/***
 * 
 * @author Marcus Laitala (Marcizz) & Emil Albrektsson (Spirotris) 2019-09-21
 *
 */
@RunWith(Parameterized.class)
public class ClientTest {

	private Client client;
	private static ChatServer server;
	private static final int portNumber = 27500;
	private final String host = "localhost";
	private Socket socket;

	PrintWriter writer;
	BufferedReader reader;
	String message;

	@Before
	public void setUp() {
		client = new Client(portNumber);
		server = new ChatServer(portNumber);

		try {
			new Thread(server).start();
			socket = new Socket(host, portNumber);

			// SocketSender
			writer = new PrintWriter(socket.getOutputStream(), true);

			// SocketListener
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		message = "Test";
	}

	@AfterClass
	public void StopOngoingProcesses() {
		server.stopServer();
	}

	@Test(timeout = 5000)
	public void testClientIsSendingConnectionRequest() {
		Assert.assertFalse(socket.isClosed());
	}

	@Test(timeout = 5000)
	public void testConnectionToServerAndSendingAMessage() {
		try {
			writer.println(message);
			String actual = reader.readLine();
			Assert.assertTrue(message.contentEquals(actual));

			reader.close();
			socket.close();

		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
	}
/*
	@Test(timeout = 7000)
	public void testSendingMultipleMessages() {
		String[] messages = new String[1];
		try {
			writer.println(message);
			String actual = reader.readLine();
			Assert.assertTrue(message.contentEquals(actual));

		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		} finally {
			try {
				reader.close();
				socket.close();
			} catch (Exception e2) {
				fail(e2.getLocalizedMessage());
			}
		}
	}
*/
}