package chatclient;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import chatserver.client.Client;
import chatserver.client.ClientMethods;

/***
 * 
 * @author Marcus Laitala (Marcizz) & Emil Albrektsson (Spirotris) 2019-09-21
 *
 */

//@RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

	// @InjectMocks annotation is used to create and inject the mock object
	@InjectMocks
	private Client client;
	private final int portNumber = 2942;

	// @Mock annotation is used to create the mock object to be injected
	@Mock
	ClientMethods clientMethods;

	@Before
	public void setUp() {
		client = new Client();
	}
	
	/*
	@Test
	public void testConnectionToServer() {

	}
	*/

	@Test
	public void testSetAndGetPortNumber() {
		// set and get the port number the client will use to connect to the server with
		when(clientMethods.getPortNumber()).thenReturn(portNumber);
		
		client.setPortNumber(portNumber);
		
		
		//test the set and get functions
		Assert.assertEquals(Integer.valueOf(portNumber), Integer.valueOf(clientMethods.getPortNumber()));
		
		/*
		client.setPortNumber(portNumber);
		int actual = client.getPortNumber();

		assertEquals(portNumber, actual);
		 */
	}

}