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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.model.LocalFile;
import util.file.MyFile;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class FileTest implements junit.framework.Test {
	private static final Logger logger = LoggerFactory.getLogger(FileTest.class);

	private static MyFile file;
	private LocalFile localFile;
	private final String fileName = "test.txt";
	private final String directory = "test\\";

	/**
	 * Test method for {@link MyFile#MyFile(LocalFile)}.
	 */
	@Test
	@Order(1)
	public void constructor() {
		logger.trace("Teste construtores - Inicio");
		file = new MyFile(new LocalFile(fileName));
		assertEquals(file.getLocalFile().getFileName(), fileName);
		file = new MyFile(fileName);
		assertEquals(file.getLocalFile().getFileName(), fileName);
		logger.trace("Teste construtores - Fim");
	}

	/**
	 * Test method for {@link MyFile#setDirectory(java.lang.String)}.
	 */
	@Test
	@Order(2)
	public void setDirectory() {
		logger.trace("Teste setDirectory - Inicio");
		file.setDirectory(directory);
		assertEquals(directory, file.getLocalFile().getDirectory());
		logger.trace("Teste setDirectory - Fim");
	}

	/**
	 * Test method for {@link MyFile#createDirectoryIfDoesntExists(java.lang.String)}.
	 */
	@Test
	@Order(2)
	public void createDirectoryIfDoesntExists() {
		logger.trace("Teste createDirectory - Inicio");
		assertTrue(file.createDirectoryIfDoesntExists(directory));
		logger.trace("Teste createDirectory - Fim");
	}

	/**
	 * Test method for {@link MyFile#createFile(java.lang.String)}.
	 */
	@Test
	@Order(3)
	public void createFile() {
		logger.trace("Teste createFile - Inicio");
		logger.trace("- Teste1: criando por string");
		assertTrue( file.createFile());

		logger.trace("- Teste1: criando pelo objeto LocalFile");
		localFile = new LocalFile();
		localFile.setDirectory(directory);
		localFile.setFileName(fileName);
		file = new MyFile(localFile);
		assertTrue( file.createFile());
		localFile = null;
		logger.trace("Teste createFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#newLine(java.lang.String)}.
	 */
	@Test
	@Order(4)
	public void newLine() {
		logger.trace("Teste newLine - Inicio");
		assertTrue( file.newLine("teste salvar uma linha no arquivo"));
		logger.trace("Teste newLine - Fim");
	}

	/**
	 * Test method for {@link MyFile#saveFile()}.
	 */
	@Test
	@Order(5)
	public void saveFile() throws IOException {
		logger.trace("Teste saveFile - Inicio");
		assertTrue( file.saveFile());
		logger.trace("Teste saveFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#openTextFile()}.
	 */
	@Test
	@Order(6)
	public void openTextFile() throws IOException {
		logger.trace("Teste openTextFile - Inicio");
		assertTrue( file.openTextFile() );
		logger.trace("Teste openTextFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#getNextLineFromFile()}.
	 */
	@Test
	@Order(7)
	public void getNextLineFromFile() throws IOException {
		logger.trace("Teste getNextLine - Inicio");
		assertTrue( file.getNextLineFromFile().length() > 10);
		logger.trace("Teste getNextLine - Fim");
	}

	/**
	 * Test method for {@link MyFile#closeTextFile()}.
	 */
	@Test
	@Order(8)
	public void closeFile() {
		logger.trace("Teste closeFile - Inicio");
		assertTrue( file.closeTextFile());
		logger.trace("Teste closeFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#renameFile(java.lang.String)}.
	 */
	@Test
	@Order(9)
	public void renameFile() throws IOException {
		logger.trace("Teste renomear arquivo - Inicio");
		logger.trace("Renomear de: " + file.getLocalFile().getFileFullName());
		logger.trace("Para: " + file.getLocalFile().getDirectory() + "_" + fileName);
		assertTrue( file.renameFile("_" + fileName) );
		logger.trace("Voltando para: " + file.getLocalFile().getDirectory() + fileName);
		assertTrue( file.renameFile(fileName) );
		logger.trace("Teste renomear arquivo - Fim");
	}

	/**
	 * Test method for {@link MyFile#getFileLastModified()}.
	 */
	@Test
	@Order(10)
	public void getFileLastModified() throws IOException {
		logger.trace("Teste getLastModified - Inicio");
		assertTrue( file.getFileLastModified().before(new GregorianCalendar()));
		logger.trace("Teste getLastModified - Fim");
	}

	/**
	 * Test method for {@link MyFile#isFile()}.
	 */
	@Test
	@Order(11)
	public void isFile() throws IOException {
		logger.trace("Teste isFile - Inicio");
		assertTrue( file.isFile());
		assertTrue( file.isExists());
		logger.trace("Teste isFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#copyFile(java.lang.String)}.
	 */
	@Test
	@Order(12)
	public void copyFile() throws IOException {
		logger.trace("Teste copyFile - Inicio");
		assertTrue( file.copyFile("copy_" + fileName) );
		assertTrue( file.copyFile(fileName) );
		logger.trace("Teste copyFile - Fim");
	}

	/**
	 * Test method for {@link MyFile#listFilesInDirectory()}.
	 */
	@Test
	@Order(13)
	public void listFilesInDirectory() {
		logger.trace("Teste listFiles - Inicio");
		assertTrue(file.listFilesInDirectory().length >= 0);
		logger.trace("Teste listFiles - Fim");
	}

	/**
	 * Test method for {@link MyFile#isFileStartsWithBOM()}.
	 */
	@Test
	@Order(14)
	public void isFileStartsWithBOM() throws IOException {
		logger.trace("Teste isFileStartsWithBOM - Inicio");
		assertTrue(!this.file.isFileStartsWithBOM());
		logger.trace("Teste isFileStartsWithBOM - Fim");
	}

	/**
	 * Test method for {@link MyFile#deleteFile()}.
	 * @throws IOException
	 */
	@Test
	@Order(15)
	public void deleteFile() throws IOException {
		assertTrue( file.deleteFile());
	}

	/**
	 * Test method for {@link MyFile#setFile(java.lang.String)}.
	 */
	@Test
	public void setFile() {
		logger.trace("Teste setFile - Inicio");
		file.setFile(fileName);
		assertEquals(fileName, file.getLocalFile().getFileName());
		logger.trace("Teste setFile - Fim");
	}

	/**'
	 * Test method for {@link MyFile#getEncode()}.
	 */
	@Test
	public void getEncode() {
		logger.trace("Teste getEncode - Inicio");
		assertEquals("UTF-8",this.file.getEncode());
		logger.trace("Teste getEncode - Fim");
	}

	/**
	 * Test method for {@link MyFile#setEncode(java.lang.String)}.
	 */
	@Test
	public void setEncode() {
		logger.trace("Teste setEncode - Inicio");
		assertEquals("UTF-8", this.file.getEncode());
		this.file.setEncode("teste");
		assertEquals("teste", this.file.getEncode());
		this.file.setEncode("UTF-8");
		assertEquals("UTF-8", this.file.getEncode());
		logger.trace("Teste setEncode - Fim");
	}

	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult testResult) {

	}
}