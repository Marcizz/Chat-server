package chatserver.client;

import java.net.Socket;
/***
 * 
 * @author Marcus Laitala (Marcizz) 2019-09-21
 *
 */
public interface ClientMethods {
	public boolean connect(String host, int portNumber, Socket socket);
	public void setPortNumber(int portNumber);
	public int getPortNumber();
}
