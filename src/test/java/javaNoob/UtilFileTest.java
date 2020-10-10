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
		System.out.println("Teste construtores - Inicio");
		file = new UtilFile(new LocalFile(fileName));
		assertEquals(file.getLocalFile().getFileName(), fileName);
		file = new UtilFile(fileName);
		assertEquals(file.getLocalFile().getFileName(), fileName);
		assertNull(file.getLocalFile().getFileName());
		System.out.println("Teste construtores - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#setArquivo(java.lang.String)}.
	 */
	@Test
	void testSetFile() {
		System.out.println("Teste setFile - Inicio");
		file = new UtilFile();
		file.setFile(fileName);
		
		assertEquals(fileName, file.getLocalFile().getFileName());
		System.out.println("Teste setFile - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#setDirectory(java.lang.String)}.
	 */
	@Test
	void testSetDirectory() {
		System.out.println("Teste setDirectory - Inicio");
		file = new UtilFile();
		file.setDirectory(directory);
		
		assertEquals(directory, file.getLocalFile().getDirectory());
		System.out.println("Teste setDirectory - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#createFile(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	void testCreateFileString() throws IOException {
		System.out.println("Teste createFile - Inicio");
		System.out.println("- Teste1: criando por string");
		file = new UtilFile(fileName);		
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste criar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.deleteFile());
		
		System.out.println("- Teste1: criando pelo objeto LocalFile");
		LocalFile localFile = new LocalFile();
		localFile.setDirectory(directory);
		localFile.setFileName(fileName);
		file = new UtilFile(localFile);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste criar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.deleteFile());
		System.out.println("Teste createFile - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#saveFile()}.
	 */
	@Test
	void testSaveFile() {
		System.out.println("Teste saveFile - Inicio");
		file = new UtilFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste salvar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.deleteFile());
		System.out.println("Teste saveFile - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#newLine(java.lang.String)}.
	 */
	@Test
	void testNewLine() {
		System.out.println("Teste newLine - Inicio");
		file = new UtilFile(fileName);
		assertTrue( file.newLine("teste salvar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.deleteFile());
		System.out.println("Teste newLine - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#renameFile(java.lang.String)}.
	 */
	@Test
	void testRenameFile() {
		System.out.println("Teste renomear arquivo - Inicio");
		file = new UtilFile(fileName);
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
	 * Test method for {@link util.UtilFile#isFile()}.
	 */
	@Test
	void testIsFile() {
		System.out.println("Teste isFile - Inicio");
		file = new UtilFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste isFile?"));
		assertTrue( file.saveFile());
		assertTrue( file.isFile());
		assertTrue( file.isExists());
		assertTrue( file.deleteFile());
		System.out.println("Teste isFile - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#createDirectoryIfDoesntExists(java.lang.String)}.
	 */
	@Test
	void testCreateDirectoryIfDoesntExists() {
		System.out.println("Teste createDirectory - Inicio");
		file = new UtilFile(fileName);
		assertTrue(file.createDirectoryIfDoesntExists(directory));
		System.out.println("Teste createDirectory - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#getFileLastModified()}.
	 */
	@Test
	void testGetFileLastModified() {
		System.out.println("Teste getLastModified - Inicio");
		file = new UtilFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste criar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.getFileLastModified().before(new GregorianCalendar()));
		assertTrue( file.deleteFile());
		System.out.println("Teste getLastModified - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#listFilesInDirectory()}.
	 */
	@Test
	void testListFilesInDirectory() {
		System.out.println("Teste listFiles - Inicio");
		file = new UtilFile(fileName);
		file.setDirectory(directory);
		assertTrue(file.listFilesInDirectory().length >= 0);
		System.out.println("Teste listFiles - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#openTextFile()}.
	 */
	@Test
	void testOpenTextFile() {
		System.out.println("Teste openTextFile - Inicio");
		file = new UtilFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste abrir arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.openTextFile() );
		assertTrue( file.closeTextFile() );
		assertTrue( file.deleteFile());
		System.out.println("Teste openTextFile - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#getNextLineFromFile()}.
	 */
	@Test
	void testGetNextLineFromFile() {
		System.out.println("Teste getNextLine - Inicio");
		file = new UtilFile(fileName);
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
	 * Test method for {@link util.UtilFile#copyFile(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testCopyFile() {
		System.out.println("Teste copyFile - Inicio");
		file = new UtilFile(fileName);
		assertTrue( file.createFile());
		assertTrue( file.newLine("teste copiar arquivo"));
		assertTrue( file.saveFile());
		assertTrue( file.copyFile("copy_" + fileName) );
		assertTrue( file.deleteFile());
		file = new UtilFile("copy_" + fileName);
		assertTrue( file.deleteFile());
		System.out.println("Teste copyFile - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#getEncode()}.
	 */
	@Test
	void testGetEncode() {
		System.out.println("Teste getEncode - Inicio");
		file = new UtilFile(fileName);
		assertEquals("UTF-8",this.file.getEncode());
		System.out.println("Teste getEncode - Fim");
	}

	/**
	 * Test method for {@link util.UtilFile#setEncode(java.lang.String)}.
	 */
	@Test
	void testSetEncode() {
		System.out.println("Teste setEncode - Inicio");
		file = new UtilFile(fileName);
		assertEquals("UTF-8", this.file.getEncode());
		this.file.setEncode("teste");
		assertEquals("teste", this.file.getEncode());
		this.file.setEncode("UTF-8");
		assertEquals("UTF-8", this.file.getEncode());
		System.out.println("Teste setEncode - Fim");
	}
	
	/**
	 * Test method for {@link util.UtilFile#isFileStartsWithBOM(java.lang.Boolean)}.
	 */
	@Test
	void testIsFileStartsWithBOM() {
		System.out.println("Teste isFileStartsWithBOM - Inicio");
		file = new UtilFile(fileName);
		assertTrue(file.createFile());
		assertTrue( file.newLine("teste is file start with UTF8 BOM ?"));
		assertTrue( file.saveFile());
		assertTrue(!this.file.isFileStartsWithBOM());
		assertTrue(file.deleteFile());
		System.out.println("Teste isFileStartsWithBOM - Fim");
	}
}