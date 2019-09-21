package chatserver.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SuppressWarnings("unused")
public class Client implements ClientMethods {
	private ClientMethods clientMethods;
	private final String host = "localhost";
	private int portNumber = 81;
	
	public Client() {	}
	

	public boolean connect(String host, int portNumber, Socket socket) {
		// TODO Auto-generated method stub
		return false;
	}


	public void setPortNumber(int portNumber) {
		// TODO Auto-generated method stub
		
	}


	public int getPortNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
