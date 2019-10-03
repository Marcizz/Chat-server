package chatserver.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	private String serverName = "127.0.0.1";
	private final static String ERROR_FINDING_SERVER = "Adress of server not found",
			IO_ERROR = "Input or output got interrupted", HOW_TO_EXIT = "Type exit to shutdown the program", PROGRAM_SHUTTING_DOWN = "Program is now closed...";
	private HandleServer server;
	private Socket socket;
	private int portNumber;

	public ChatClient(int portNumber) {
		this.portNumber = portNumber;
		initiateClient();
	}

	public ChatClient(String serverName, int portNumber) {
		this.serverName = serverName;
		this.portNumber = portNumber;
		initiateClient();
	}

	public void initiateClient() {
		try {
			System.out.println("Connecting to " + serverName + " on port " + portNumber);
			socket = new Socket(serverName, portNumber);
			System.out.println("Just connected to " + socket.getRemoteSocketAddress());
			System.out.println("Client up and running" + "\n" + HOW_TO_EXIT);
			server = new HandleServer(socket);
		} catch (UnknownHostException e) {
			System.err.println(ERROR_FINDING_SERVER + ":\n" + e.getLocalizedMessage() + e.getStackTrace());
			stopClient();
		} catch (IOException e) {
			System.err.println(IO_ERROR + ":\n" + e.getLocalizedMessage() + e.getStackTrace());
			stopClient();
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage() + ":\n" + e.getStackTrace());
			stopClient();
		}
	}

	public void stopClient() {
		if (!socket.isClosed()) {
			try {
				server.setExit(true);
				socket.shutdownInput();
				socket.shutdownOutput();				
				socket.close();
				System.out.println(PROGRAM_SHUTTING_DOWN);
				System.exit(java.lang.Thread.NORM_PRIORITY);	
			} catch (IOException e) {
				System.err.println(getClass() + " : " + e.getLocalizedMessage() + "\n" + e.getStackTrace());
			}
		}
	}

	public HandleServer getServer() {
		return server;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public boolean isConnected() {
		return !socket.isClosed();
	}

}
