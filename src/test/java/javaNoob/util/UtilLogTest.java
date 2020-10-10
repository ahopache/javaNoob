/**
* encode: utf-8
*
* @author: Assis Henrique Oliveira Pacheco
*/
package javaNoob.util;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import util.UtilLog;

class UtilLogTest {

	@Test
	void testSetLogString() {
		assertTrue(UtilLog.setLog("teste de log"));
	}

	@Test
	void testSetLogStringString() {
		UtilLog.setLog("teste", "teste de log");
	}
}