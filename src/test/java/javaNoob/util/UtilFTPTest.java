package javaNoob.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;

import util.UtilFTPClient;
import util.UtilFTPServer;

@TestMethodOrder(OrderAnnotation.class)
public class UtilFTPTest {
	private UtilFTPClient ftpClient;
	private UtilFTPServer ftpServer;
	
	
	/**
	 * Setup para o UtilFTPServer
	 * 
	 * @throws IOException
	 */
	@Before
	public void setup() {
		ftpServer = new UtilFTPServer();
		ftpClient = new UtilFTPClient();
		
		ftpServer.start();
		ftpServer.addUser("userTest", "passTest");
 	}
	
	@After
	public void teardown() {
		ftpClient.disconnect();
		ftpServer.stop();
	}
	
	@Test
	public void connect() {
		assertTrue(ftpClient.connect("localhost", "userTest", "passTest"));
	}
}