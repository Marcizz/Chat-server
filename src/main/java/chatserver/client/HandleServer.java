package chatserver.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import chatserver.server.ChatServer;

public class HandleServer extends Thread {
	BufferedReader input, userInput;
	PrintWriter output;

	private final static String CLIENT_STARTED = "Client started...", EXIT_TEXT = "exit", QUIT_TEXT = "quit",
			CONNECTION_CLOSED_BY_USER_REQUEST = "Connection closed", ASK_FOR_USER_NAME = "Enter you username: ",
			USERNAME_REGISTERED_ON_SERVER = "Username sent to server";
	private String userName = "";
	private boolean exit = false;
	private Socket server;
	private Thread readingService;

	public HandleServer(Socket server) throws IOException {
		this.server = server;
		input = new BufferedReader(new InputStreamReader(server.getInputStream()));
		userInput = new BufferedReader(new InputStreamReader(System.in));
		output = new PrintWriter(server.getOutputStream(), true);
		System.out.println(CLIENT_STARTED);

		setUserNameAndSendToServer();
		System.out.println(USERNAME_REGISTERED_ON_SERVER);
		setupReadingService();
		this.start();
	}

	public void readMessage() throws IOException {
		System.out.println(input.readLine());
	}

	public void sendMessage(String message) throws IOException {
		if (message.isEmpty()) {
			return;
		} else if (message.equalsIgnoreCase((EXIT_TEXT)) || message.equalsIgnoreCase((QUIT_TEXT))) {
			exit = true;
			System.out.println(CONNECTION_CLOSED_BY_USER_REQUEST);
			return;
		}
		output.println(message);
	}

	public void setUserNameAndSendToServer() throws IOException {
		System.out.print(ASK_FOR_USER_NAME);
		userName = userInput.readLine();
		output.println(userName);
	}

	public void setupReadingService() {
		// Start new thread for sending messages
		// Sending and receiving can now be done asynchronously/without interrupting
		// eachother
		readingService = new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						readMessage();
					}
				} catch (IOException e) {
					System.err.println(getClass() + " : " + e.getLocalizedMessage() + "\n" + e.getStackTrace());
					e.printStackTrace();
				}
			}
		});
		readingService.start();
	}

	public void run() {
		try {
			while (!exit) {
				sendMessage(userInput.readLine());
			}
		} catch (Exception e) {
			System.err.println(getClass() + " : " + e.getLocalizedMessage() + "\n" + e.getStackTrace());
			try {
				server.close();
			} catch (IOException e1) {
				System.err.println(getClass() + " : " + e1.getLocalizedMessage() + "\n" + e1.getStackTrace());
			}
		}
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}
}
