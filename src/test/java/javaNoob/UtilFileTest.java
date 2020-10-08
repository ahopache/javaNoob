/**
* encode: utf-8
*
* @author: Assis Henrique Oliveira Pacheco
*/
package javaNoob;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import util.LocalFile;
import util.UtilFile;

@TestMethodOrder(OrderAnnotation.class)
class UtilFileTest {
	private UtilFile file;
	private String fileName = "test.txt";
	private String directory = "test\\";
	
	/**
	 * Test method for {@link util.UtilFile#UtilFile(util.LocalFile)}.
	 */
	@Before
	void testUtilFileConstructor() {
		file = new UtilFile(new LocalFile(fileName));
		assertEquals(file.getLocalFile().getFileName(), fileName);
		file = new UtilFile(fileName);
		assertEquals(file.getLocalFile().getFileName(), fileName);
		assertNull(file.getLocalFile().getFileName());
		
	}

	/**
	 * Test method for {@link util.UtilFile#setArquivo(java.lang.String)}.
	 */
	@Test
	void testSetFile() {
		file = new UtilFile();
		file.setFile(fileName);
		
		assertEquals(fileName, file.getLocalFile().getFileName());
	}

	/**
	 * Test method for {@link util.UtilFile#setDirectory(java.lang.String)}.
	 */
	@Test
	void testSetDirectory() {
		file = new UtilFile();
		file.setDirectory(directory);
		
		assertEquals(directory, file.getLocalFile().getDirectory());
	}

	/**
	 * Test method for {@link util.UtilFile#createFile(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	void testCreateFileString() throws IOException {
		file = new UtilFile(fileName);
		
		assertTrue(file.createFile());
	}

	/**
	 * Test method for {@link util.UtilFile#createFile()}.
	 */
	@Test
	void testCreateFile() {
		LocalFile localFile = new LocalFile();
		localFile.setDirectory(directory);
		localFile.setFileName(fileName);
		file = new UtilFile(localFile);
		
		assertTrue(file.createFile(fileName));
	}
	
	/**
	 * Test method for {@link util.UtilFile#saveFile()}.
	 */
	@Test
	void testSaveFile() {
		file = new UtilFile(fileName);
		file.openTextFile();
		assertTrue(file.newLine("teste save"));
		file.saveFile();
		file.closeTextFile();
	}

	/**
	 * Test method for {@link util.UtilFile#newLine(java.lang.String)}.
	 */
	@Test
	void testNewLine() {
		file = new UtilFile(fileName);
		file.openTextFile();
		assertTrue(file.newLine("teste linha 1"));
		file.saveFile();
		file.closeTextFile();
	}

	/**
	 * Test method for {@link util.UtilFile#renameFile(java.lang.String)}.
	 */
	@Test
	void testRenameFile() {
		file = new UtilFile(fileName);
		assertTrue( file.renameFile("_" + fileName) );
		assertTrue( file.renameFile(fileName) );
	}

	/**
	 * Test method for {@link util.UtilFile#isFile()}.
	 */
	@Test
	void testIsFile() {
		file = new UtilFile(fileName);
		assertTrue(file.isFile());
		assertTrue(file.isExists());
	}

	/**
	 * Test method for {@link util.UtilFile#createDirectoryIfDoesntExists(java.lang.String)}.
	 */
	@Test
	void testCreateDirectoryIfDoesntExists() {
		file = new UtilFile(fileName);
		assertTrue(file.createDirectoryIfDoesntExists(directory));
		
	}

	/**
	 * Test method for {@link util.UtilFile#getFileLastModified()}.
	 */
	@Test
	void testGetFileLastModified() {
		file = new UtilFile(fileName);
		assertTrue(file.getFileLastModified().before(new GregorianCalendar()));
	}

	/**
	 * Test method for {@link util.UtilFile#listFilesInDirectory()}.
	 */
	@Test
	void testListFilesInDirectory() {
		file = new UtilFile(fileName);
		file.setDirectory(directory);
		assertTrue(file.listFilesInDirectory().length >= 0);
	}

	/**
	 * Test method for {@link util.UtilFile#openTextFile()}.
	 */
	@Test
	void testOpenTextFile() {
		file = new UtilFile(fileName);
		assertTrue( file.openTextFile() );
	}

	/**
	 * Test method for {@link util.UtilFile#getNextLineFromFile()}.
	 */
	@Test
	void testGetNextLineFromFile() {
		file = new UtilFile(fileName);
		file.openTextFile();
		assertTrue(file.getNextLineFromFile().length() >= 0);
	}

	/**
	 * Test method for {@link util.UtilFile#copyFile(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testCopyFile() {
		file = new UtilFile(fileName);
		assertTrue( file.copyFile("copy_" + fileName) );
		file = new UtilFile("copy_" + fileName);
		assertTrue( file.deleteFile());
	}

	/**
	 * Test method for {@link util.UtilFile#getEncode()}.
	 */
	@Test
	void testGetEncode() {
		file = new UtilFile(fileName);
		assertEquals("UTF-8",this.file.getEncode());
	}

	/**
	 * Test method for {@link util.UtilFile#setEncode(java.lang.String)}.
	 */
	@Test
	void testSetEncode() {
		file = new UtilFile(fileName);
		assertEquals("UTF-8", this.file.getEncode());
		this.file.setEncode("teste");
		assertEquals("teste", this.file.getEncode());
		this.file.setEncode("UTF-8");
		assertEquals("UTF-8", this.file.getEncode());
	}
	
	/**
	 * Test method for {@link util.UtilFile#isFileStartsWithBOM(java.lang.Boolean)}.
	 */
	@Test
	void testIsFileStartsWithBOM() {
		file = new UtilFile(fileName);
		assertTrue(!this.file.isFileStartsWithBOM());
	}

}
