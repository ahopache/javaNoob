package javaNoob.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;

import util.UtilFTPClient;
import util.UtilFTPServer;

@TestMethodOrder(OrderAnnotation.class)
public class FTPTest {
	private UtilFTPClient ftpClient;
	private UtilFTPServer ftpServer;

	/**'
	 * Test method for {@link UtilFTPClient#connect(java.lang.String, java.lang.String, java.lang.String, Integer)}.
	 */
	@Test
	public void connectFTP() {
		Boolean connect;
		ftpServer = new UtilFTPServer(2121);
		ftpClient = new UtilFTPClient();

		ftpServer.start();
		connect = ftpClient.connect("localhost", "admin", "admin", 2121);

		ftpClient.disconnect();
		ftpServer.stop();
		assertTrue(connect);
	}
}