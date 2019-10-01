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
			ASK_FOR_USER_NAME = "Enter you username:\n", USERNAME_REGISTERED_ON_SERVER = "\nUsername sent to server";
	private String userName = "";
	private static final int MILLIS_WAIT_BETWEEN_MESSAGES = 100;
	private boolean exit = false;
	private Socket server;

	public HandleServer(Socket server) throws IOException {
		this.server = server;
		input = new BufferedReader(new InputStreamReader(server.getInputStream()));
		userInput = new BufferedReader(new InputStreamReader(System.in));
		output = new PrintWriter(server.getOutputStream(), true);
		System.out.println(CLIENT_STARTED);
		this.start();
	}

	public void readMessage() throws IOException {
		if (input.ready()) {
			System.out.println(input.readLine());
		}
	}

	public void sendMessage(String message) throws IOException {
		if (message.isEmpty()) {
			return;
		} else if (message.equalsIgnoreCase((EXIT_TEXT)) || message.equalsIgnoreCase((QUIT_TEXT))) {
			exit = true;
			// TODO: Add text sending that the user has exited to server
			return;
		}
		output.println(message);
	}

	public void setUserNameAndSendToServer() throws IOException {
		System.out.println(ASK_FOR_USER_NAME);
		userName = userInput.readLine();
		output.println(userName);
	}

	public void run() {
		try {
			setUserNameAndSendToServer();
			System.out.println(userName + USERNAME_REGISTERED_ON_SERVER);
			
			//Start new thread for sending messages
			//Now sending and receiving can be done asynchronously/without interrupting eachother
			Thread t1 = new Thread(new Runnable() {
				public void run() {
					try {
						sendMessage(userInput.readLine());
						sleep(MILLIS_WAIT_BETWEEN_MESSAGES);
					} catch (IOException e) {
						System.err.println(getClass() + " : " + e.getLocalizedMessage() + "\n" + e.getStackTrace());
						e.printStackTrace();
					} catch (InterruptedException e) {
						System.err.println(getClass() + " : " + e.getLocalizedMessage() + "\n" + e.getStackTrace());
					}

				}
			});
			t1.start();
			
			while (!exit) {
				sendMessage(userInput.readLine());
				sleep(MILLIS_WAIT_BETWEEN_MESSAGES);
				// Waits to read a message from server until user input is complete
				readMessage();
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
