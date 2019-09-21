package chatserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	private ServerSocket server;

	public ChatServer(int port) {
		try {
			System.out.println("Waiting for client on port " + port);
			server = new ServerSocket(port);

			Socket socket = server.accept();
			
			InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println(reader.readLine());
            
			server.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ServerSocket getSocket() {
		return server;
	}

}
