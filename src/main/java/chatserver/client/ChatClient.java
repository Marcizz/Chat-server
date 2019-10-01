package chatserver.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	private String serverName = "127.0.0.1";
	private final static String ERROR_FINDING_SERVER = "Adress of server not found",
			IO_ERROR = "Input or output got interrupted ";
	private handleServer server;
	private Socket socket;

	public ChatClient(int portNumber) {
		try {
			System.out.println("Connecting to " + serverName + " on port " + portNumber);
			socket = new Socket(serverName, portNumber);
			System.out.println("Just connected to " + socket.getRemoteSocketAddress());
			server = new handleServer(socket);
		} catch (UnknownHostException e) {
			System.err.println(ERROR_FINDING_SERVER + ":\n" + e.getLocalizedMessage());
			e.printStackTrace();
			stopClient();
		} catch (IOException e) {
			System.err.println(IO_ERROR + ":\n" + e.getLocalizedMessage() + e.getStackTrace());
			stopClient();
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage() + ":\n" + e.getStackTrace());
			stopClient();
		} finally {
			System.out.println("Client up and running");
		}
	}

	public void stopClient() {
		if (!socket.isClosed()) {
			try {
				socket.close();
				server.setExit(true);
			} catch (IOException e) {
				System.err.println(getClass() + " : " + e.getLocalizedMessage() + "\n" + e.getStackTrace());
			}
		}
	}

	public handleServer getServer() {
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
