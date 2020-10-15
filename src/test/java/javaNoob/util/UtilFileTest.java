/**
* encode: utf-8
*
* @author: Assis Henrique Oliveira Pacheco
*/
package javaNoob.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import util.model.LocalFile;
import util.file.MyFile;

@TestMethodOrder(OrderAnnotation.class)
class UtilFileTest {
	private MyFile file;
	private String fileName = "test.txt";
	private String directory = "test\\";
	private LocalFile localFile;
	
	/**
	 * Test method for {@link MyFile#MyFile(LocalFile)}.
	 */
	@Before
	void testUtilFileConstructor() {
		System.out.println("Teste construtores - Inicio");
		file = new MyFile(new LocalFile(fileName));
		assertEquals(file.getLocalFile().getFileName(), fileName);
		file = new MyFile(fileName);
		assertEquals(file.getLocalFile().getFileName(), fileName);
		assertNull(file.getLocalFile().getFileName());
		System.out.println("Teste construtores - Fim");
	}

	/**
	 * Test method for {@link MyFile#setFile(java.lang.String)}.
	 */
	@Test
	void testSetFile() {
		System.out.println("Teste setFile - Inicio");
		file = new MyFile();
		file.setFile(fileName);
		
		assertEquals(fileName, file.getLocalFile().getFileName());
		System.out.println("Teste setFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#setDirectory(java.lang.String)}.
	 */
	@Test
	void testSetDirectory() {
		System.out.println("Teste setDirectory - Inicio");
		file = new MyFile();
		file.setDirectory(directory);
		
		assertEquals(directory, file.getLocalFile().getDirectory());
		System.out.println("Teste setDirectory - Fim");
	}

	/**
	 * Test method for {@link MyFile#createFile(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	void testCreateFileString() throws IOException {
		System.out.println("Teste createFile - Inicio");
		System.out.println("- Teste1: criando por string");
		file = new MyFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste criar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.deleteFile());
		
		System.out.println("- Teste1: criando pelo objeto LocalFile");
		localFile = new LocalFile();
		localFile.setDirectory(directory);
		localFile.setFileName(fileName);
		file = new MyFile(localFile);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste criar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.deleteFile());
		localFile = null;
		System.out.println("Teste createFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#saveFile()}.
	 */
	@Test
	void testSaveFile() {
		System.out.println("Teste saveFile - Inicio");
		file = new MyFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste salvar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.deleteFile());
		System.out.println("Teste saveFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#newLine(java.lang.String)}.
	 */
	@Test
	void testNewLine() {
		System.out.println("Teste newLine - Inicio");
		file = new MyFile(fileName);
		assertTrue( file.newLine("teste salvar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.deleteFile());
		System.out.println("Teste newLine - Fim");
	}

	/**
	 * Test method for {@link MyFile#renameFile(java.lang.String)}.
	 */
	@Test
	void testRenameFile() {
		System.out.println("Teste renomear arquivo - Inicio");
		file = new MyFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste renomear arquivo"));
		assertTrue( file.saveFile());
		System.out.println("Renomear de: " + file.getLocalFile().getFileFullName());
		System.out.println("Para: " + file.getLocalFile().getDirectory() + "_" + fileName);
		assertTrue( file.renameFile("_" + fileName) );
		assertTrue( file.renameFile(fileName) );
		assertTrue( file.deleteFile());
		System.out.println("Teste renomear arquivo - Fim");
	}

	/**
	 * Test method for {@link MyFile#isFile()}.
	 */
	@Test
	void testIsFile() {
		System.out.println("Teste isFile - Inicio");
		file = new MyFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste isFile?"));
		assertTrue( file.saveFile());
		assertTrue( file.isFile());
		assertTrue( file.isExists());
		assertTrue( file.deleteFile());
		System.out.println("Teste isFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#createDirectoryIfDoesntExists(java.lang.String)}.
	 */
	@Test
	void testCreateDirectoryIfDoesntExists() {
		System.out.println("Teste createDirectory - Inicio");
		file = new MyFile(fileName);
		assertTrue(file.createDirectoryIfDoesntExists(directory));
		System.out.println("Teste createDirectory - Fim");
	}

	/**
	 * Test method for {@link MyFile#getFileLastModified()}.
	 */
	@Test
	void testGetFileLastModified() {
		System.out.println("Teste getLastModified - Inicio");
		file = new MyFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste criar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.getFileLastModified().before(new GregorianCalendar()));
		assertTrue( file.deleteFile());
		System.out.println("Teste getLastModified - Fim");
	}

	/**
	 * Test method for {@link MyFile#listFilesInDirectory()}.
	 */
	@Test
	void testListFilesInDirectory() {
		System.out.println("Teste listFiles - Inicio");
		file = new MyFile(fileName);
		file.setDirectory(directory);
		assertTrue(file.listFilesInDirectory().length >= 0);
		System.out.println("Teste listFiles - Fim");
	}

	/**
	 * Test method for {@link MyFile#openTextFile()}.
	 */
	@Test
	void testOpenTextFile() {
		System.out.println("Teste openTextFile - Inicio");
		file = new MyFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste abrir arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.openTextFile() );
		assertTrue( file.closeTextFile() );
		assertTrue( file.deleteFile());
		System.out.println("Teste openTextFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#getNextLineFromFile()}.
	 */
	@Test
	void testGetNextLineFromFile() {
		System.out.println("Teste getNextLine - Inicio");
		file = new MyFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste ler linha arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.openTextFile());
		assertTrue( file.getNextLineFromFile().length() > 10);
		assertTrue( file.closeTextFile());
		assertTrue( file.deleteFile());
		System.out.println("Teste getNextLine - Fim");
	}

	/**
	 * Test method for {@link MyFile#copyFile(java.lang.String)}.
	 */
	@Test
	void testCopyFile() {
		System.out.println("Teste copyFile - Inicio");
		file = new MyFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste copiar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.copyFile("copy_" + fileName) );
		assertTrue( file.deleteFile());
		file = new MyFile("copy_" + fileName);
		assertTrue( file.deleteFile());
		System.out.println("Teste copyFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#getEncode()}.
	 */
	@Test
	void testGetEncode() {
		System.out.println("Teste getEncode - Inicio");
		file = new MyFile(fileName);
		assertEquals("UTF-8",this.file.getEncode());
		System.out.println("Teste getEncode - Fim");
	}

	/**
	 * Test method for {@link MyFile#setEncode(java.lang.String)}.
	 */
	@Test
	void testSetEncode() {
		System.out.println("Teste setEncode - Inicio");
		file = new MyFile(fileName);
		assertEquals("UTF-8", this.file.getEncode());
		this.file.setEncode("teste");
		assertEquals("teste", this.file.getEncode());
		this.file.setEncode("UTF-8");
		assertEquals("UTF-8", this.file.getEncode());
		System.out.println("Teste setEncode - Fim");
	}
	
	/**
	 * Test method for {@link MyFile#isFileStartsWithBOM()}.
	 */
	@Test
	void testIsFileStartsWithBOM() {
		System.out.println("Teste isFileStartsWithBOM - Inicio");
		file = new MyFile(fileName);
		assertTrue(file.createFile());
		assertTrue( file.newLine("teste is file start with UTF8 BOM ?"));
		assertTrue( file.saveFile());
		assertTrue(!this.file.isFileStartsWithBOM());
		assertTrue(file.deleteFile());
		System.out.println("Teste isFileStartsWithBOM - Fim");
	}
}