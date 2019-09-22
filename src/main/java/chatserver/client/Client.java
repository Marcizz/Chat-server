package chatserver.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	private final String host = "localhost";
	private String userName = "";
	private int portNumber;
	private Socket socket;
	
	public Client(int portNumber) {
		this.portNumber = portNumber;		
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
	}


	
}
