/*
* encode: utf-8
*
* @author: Assis Henrique Oliveira Pacheco
*/
package javaNoob.util;

import java.io.IOException;
import java.util.GregorianCalendar;

import junit.framework.TestResult;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import util.model.LocalFile;
import util.file.MyFile;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class FileTest implements junit.framework.Test {
	private static MyFile file;
	private LocalFile localFile;
	private final String fileName = "test.txt";
	private final String directory = "test\\";

	/**
	 * Test method for {@link MyFile#deleteFile()}.
	 * @throws IOException
	 */
	@Test
	public void deleteFile() throws IOException {
		file = new MyFile(new LocalFile(fileName));
		assertEquals(file.getLocalFile().getFileName(), fileName);
		file = new MyFile(fileName);
		assertEquals(file.getLocalFile().getFileName(), fileName);
		file.setDirectory(directory);
		assertTrue(file.createDirectoryIfDoesntExists(directory));
		assertEquals(directory, file.getLocalFile().getDirectory());

		assertTrue( file.createFile());

		localFile = new LocalFile();
		localFile.setDirectory(directory);
		localFile.setFileName(fileName);
		file = new MyFile(localFile);
		assertTrue( file.createFile());
		localFile = null;

		assertTrue( file.newLine("teste salvar uma linha no arquivo"));
		assertTrue( file.saveFile());

		assertTrue( file.openTextFile() );
		assertTrue( file.getNextLineFromFile().length() > 10);
		assertTrue( file.closeTextFile());
		assertTrue( file.renameFile("_" + fileName) );
		assertTrue( file.renameFile(fileName) );
		assertTrue( file.getFileLastModified().before(new GregorianCalendar()));
		assertTrue( file.isFile());
		assertTrue( file.isExists());
		assertTrue( file.copyFile("copy_" + fileName) );
		assertTrue( file.copyFile(fileName) );
		assertTrue(file.listFilesInDirectory().length >= 0);
		assertTrue(!this.file.isFileStartsWithBOM());

		assertTrue( file.deleteFile());
	}

	/**
	 * Test method for {@link MyFile#setFile(java.lang.String)}.
	 */
	@Test
	public void setFile() {
		file = new MyFile(new LocalFile(fileName));
		file.setFile(fileName);
		assertEquals(fileName, file.getLocalFile().getFileName());
	}

	/**'
	 * Test method for {@link MyFile#getEncode()}.
	 */
	@Test
	public void getEncode() {
		file = new MyFile(new LocalFile(fileName));
		assertEquals("UTF-8", file.getEncode());
	}

	/**
	 * Test method for {@link MyFile#setEncode(java.lang.String)}.
	 */
	@Test
	public void setEncode() {
		file = new MyFile(new LocalFile(fileName));
		assertEquals("UTF-8", file.getEncode());
		file.setEncode("teste");
		assertEquals("teste", file.getEncode());
		file.setEncode("UTF-8");
		assertEquals("UTF-8", file.getEncode());
	}

	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult testResult) {

	}
}