/*
* encode: utf-8
*
* @author: Assis Henrique Oliveira Pacheco
*/
package javaNoob.util;

import java.io.IOException;
import java.util.GregorianCalendar;

import junit.framework.TestResult;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.junit.runners.JUnit4;
import util.model.LocalFile;
import util.file.MyFile;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class FileTest implements junit.framework.Test {
	private static MyFile file;
	private LocalFile localFile;
	private final String fileName = "test.txt";
	private final String directory = "test\\";

	/**
	 * Test method for {@link MyFile#MyFile(LocalFile)}.
	 */
	@Test
	@Order(1)
	void testConstructor() {
		System.out.println("Teste construtores - Inicio");
		file = new MyFile(new LocalFile(fileName));
		assertEquals(file.getLocalFile().getFileName(), fileName);
		file = new MyFile(fileName);
		assertEquals(file.getLocalFile().getFileName(), fileName);
		System.out.println("Teste construtores - Fim");
	}

	/**
	 * Test method for {@link MyFile#setDirectory(java.lang.String)}.
	 */
	@Test
	@Order(2)
	void testSetDirectory() {
		System.out.println("Teste setDirectory - Inicio");
		file.setDirectory(directory);
		assertEquals(directory, file.getLocalFile().getDirectory());
		System.out.println("Teste setDirectory - Fim");
	}

	/**
	 * Test method for {@link MyFile#createDirectoryIfDoesntExists(java.lang.String)}.
	 */
	@Test
	@Order(2)
	void testCreateDirectoryIfDoesntExists() {
		System.out.println("Teste createDirectory - Inicio");
		assertTrue(file.createDirectoryIfDoesntExists(directory));
		System.out.println("Teste createDirectory - Fim");
	}

	/**
	 * Test method for {@link MyFile#createFile(java.lang.String)}.
	 */
	@Test
	@Order(3)
	void testCreateFile() {
		System.out.println("Teste createFile - Inicio");
		System.out.println("- Teste1: criando por string");
		assertTrue( file.createFile());

		System.out.println("- Teste1: criando pelo objeto LocalFile");
		localFile = new LocalFile();
		localFile.setDirectory(directory);
		localFile.setFileName(fileName);
		file = new MyFile(localFile);
		assertTrue( file.createFile());
		localFile = null;
		System.out.println("Teste createFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#newLine(java.lang.String)}.
	 */
	@Test
	@Order(4)
	void testNewLine() {
		System.out.println("Teste newLine - Inicio");
		assertTrue( file.newLine("teste salvar uma linha no arquivo"));
		System.out.println("Teste newLine - Fim");
	}

	/**
	 * Test method for {@link MyFile#saveFile()}.
	 */
	@Test
	@Order(5)
	void testSaveFile() throws IOException {
		System.out.println("Teste saveFile - Inicio");
		assertTrue( file.saveFile());
		System.out.println("Teste saveFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#openTextFile()}.
	 */
	@Test
	@Order(6)
	void testOpenTextFile() throws IOException {
		System.out.println("Teste openTextFile - Inicio");
		assertTrue( file.openTextFile() );
		System.out.println("Teste openTextFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#getNextLineFromFile()}.
	 */
	@Test
	@Order(7)
	void testGetNextLineFromFile() throws IOException {
		System.out.println("Teste getNextLine - Inicio");
		assertTrue( file.getNextLineFromFile().length() > 10);
		System.out.println("Teste getNextLine - Fim");
	}

	/**
	 * Test method for {@link MyFile#closeTextFile()}.
	 */
	@Test
	@Order(8)
	void testCloseFile() {
		System.out.println("Teste closeFile - Inicio");
		assertTrue( file.closeTextFile());
		System.out.println("Teste closeFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#renameFile(java.lang.String)}.
	 */
	@Test
	@Order(9)
	void testRenameFile() throws IOException {
		System.out.println("Teste renomear arquivo - Inicio");
		System.out.println("Renomear de: " + file.getLocalFile().getFileFullName());
		System.out.println("Para: " + file.getLocalFile().getDirectory() + "_" + fileName);
		assertTrue( file.renameFile("_" + fileName) );
		System.out.println("Voltando para: " + file.getLocalFile().getDirectory() + fileName);
		assertTrue( file.renameFile(fileName) );
		System.out.println("Teste renomear arquivo - Fim");
	}

	/**
	 * Test method for {@link MyFile#getFileLastModified()}.
	 */
	@Test
	@Order(10)
	void testGetFileLastModified() throws IOException {
		System.out.println("Teste getLastModified - Inicio");
		assertTrue( file.getFileLastModified().before(new GregorianCalendar()));
		System.out.println("Teste getLastModified - Fim");
	}

	/**
	 * Test method for {@link MyFile#isFile()}.
	 */
	@Test
	@Order(11)
	void testIsFile() throws IOException {
		System.out.println("Teste isFile - Inicio");
		assertTrue( file.isFile());
		assertTrue( file.isExists());
		System.out.println("Teste isFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#copyFile(java.lang.String)}.
	 */
	@Test
	@Order(12)
	void testCopyFile() throws IOException {
		System.out.println("Teste copyFile - Inicio");
		assertTrue( file.copyFile("copy_" + fileName) );
		assertTrue( file.copyFile(fileName) );
		System.out.println("Teste copyFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#listFilesInDirectory()}.
	 */
	@Test
	@Order(13)
	void testListFilesInDirectory() {
		System.out.println("Teste listFiles - Inicio");
		assertTrue(file.listFilesInDirectory().length >= 0);
		System.out.println("Teste listFiles - Fim");
	}

	/**
	 * Test method for {@link MyFile#isFileStartsWithBOM()}.
	 */
	@Test
	@Order(14)
	void testIsFileStartsWithBOM() throws IOException {
		System.out.println("Teste isFileStartsWithBOM - Inicio");
		assertTrue(!this.file.isFileStartsWithBOM());
		System.out.println("Teste isFileStartsWithBOM - Fim");
	}

	/**
	 * Test method for {@link MyFile#deleteFile()}.
	 * @throws IOException
	 */
	@Test
	@Order(15)
	void testDeleteFile() throws IOException {
		assertTrue( file.deleteFile());
	}

	/**
	 * Test method for {@link MyFile#setFile(java.lang.String)}.
	 */
	@Test
	void testSetFile() {
		System.out.println("Teste setFile - Inicio");
		file.setFile(fileName);
		assertEquals(fileName, file.getLocalFile().getFileName());
		System.out.println("Teste setFile - Fim");
	}

	/**'
	 * Test method for {@link MyFile#getEncode()}.
	 */
	@Test
	void testGetEncode() {
		System.out.println("Teste getEncode - Inicio");
		assertEquals("UTF-8",this.file.getEncode());
		System.out.println("Teste getEncode - Fim");
	}

	/**
	 * Test method for {@link MyFile#setEncode(java.lang.String)}.
	 */
	@Test
	void testSetEncode() {
		System.out.println("Teste setEncode - Inicio");
		assertEquals("UTF-8", this.file.getEncode());
		this.file.setEncode("teste");
		assertEquals("teste", this.file.getEncode());
		this.file.setEncode("UTF-8");
		assertEquals("UTF-8", this.file.getEncode());
		System.out.println("Teste setEncode - Fim");
	}

	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult testResult) {

	}
}