/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 *
 * @docs: https://commons.apache.org/proper/commons-net/
 */
package util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;

public class UtilFTPClient {
	private static final Logger logger = LoggerFactory.getLogger(UtilFTPServer.class);

	protected FTPClient client;
	
	protected String host;
	protected String user;
	protected String pass;
	protected Integer port;
	private String protocol = null;
	
	@Deprecated
	protected String pastaAtualFTP;
	
	protected static String pastaLocal = System.getProperty("user.home") + File.separatorChar + "Documents\\Repositorio\\";
	
	/**
	 * Construtor vazio
	 */
	public UtilFTPClient(){	}
	
	/**
	 * Construtor já conectando ao FTP
	 * 
	 * @param host -> endereço do FTP para conectar
	 * @param user -> usuario
	 * @param pass -> senha
	 */
	public UtilFTPClient(String host, String user, String pass){
		this.connect(host, user, pass);
	}
	

	/**
	 * Método para reconexão, caso ocorra algum imprevisto durante a execução. Exemplo: queda de sinal ou timeout
	 */
	private void connect(){
		if(this.port == null)
			this.connect(user, user, pass);
		else
			this.connect(user, user, pass, port);
	}

	/**
	 * Método para conectar ao FTP usando a porta padrão
	 * 
	 * @param host -> endereço FTP para conectar
	 * @param user -> usuario
	 * @param pass -> senha
	 * 
	 * @return true/false se conseguiu conectar
	 */
	public boolean connect(String host, String user, String pass){
		this.host = host;
		this.user = user;
		this.pass = pass;
		//if(protocol == null){
			return this.connectFTP(host, user, pass);
		/*}else if(protocol.equals("SSL")){
			//TODO: Criar script para conectar usando protocolo SSL
			return false;
		}
		return false;*/
	}

	/**
	 * Método para conectar ao FTP usando porta diferente do padrão
	 * 
	 * @param host -> endereço do FTP para conectar
	 * @param user -> usuario
	 * @param pass -> senha
	 * @param port -> porta
	 * 
	 * @return true/false se conseguiu conectar
	 */
	public boolean connect(String host, String user, String pass, Integer port){
		this.user = user;
		this.pass = pass;
		this.host = host;
		this.port = port;

		boolean status = true;

		try {
			client = new FTPClient();
			client.connect( host, port );
			client.login( user, pass );

		} catch (Exception e) {
			logger.error("Exception");
			logger.error("UtilFTP.connect('" + host + "', '" + user + "', **password**, " + port + ")\nException: " + e.getMessage());
			status = false;
		} finally {
			if(status == true)
				logger.error("FTP HOST " + host + " conectou..." );
		}

		return status;
	}
	
	public void connectSFTP(){
		//TODO: método para conexão usando protocolo SFTP
	}

	/**
	 * Método para conectar ao FTP
	 * exibe 2 mensagens ao usuario perguntando usuario e senha de conexão
	 * 
	 * @param host -> endereço do FTP para conectar
	 * 
	 * @return true/false se conseguiu conectar
	 */
	public void connectFTP( String host ) {
		this.host = host;
		
		this.getUserJOptionPane();
		this.getPassJOptionPane();
		connectFTP(host, this.user, this.pass);
	}

	/**
	 * Método para realizar a conexão a um FTP
	 *
	 * @param host  -> HOST
	 * @param user  -> Usuario de conexão
	 * @param pass  -> Senha
	 * 
	 * @return true/false se conseguiu conectar
	 */
	public boolean connectFTP(String host, String user, String pass){
		this.user = user;
		this.pass = pass;
		this.host = host;
		
		try {
			client = new FTPClient();
			client.login( user, pass );
		} catch (Exception e) {
			logger.error("UtilFTP.connectFTP('" + host + "', '" + user + "', **password**)\nException: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Método para mostrar uma janela questionando o usuario qual usuario usar para conectar ao FTP.
	 * Ele já é pré-preenchido o usuario logado
	 * 
	 * @return String com o usuario de conexão ao FTP
	 */
	public String getUserJOptionPane(){
		String user = JOptionPane.showInputDialog( "Digite seu usuario:",System.getProperty("user.name") );
		this.user = user;
		
		return user;
	}
	
	/**
	 * Método para mostrar uma janela questionando a senha para conectar ao FTP.
	 * 
	 * @return String com a senha de conexão ao FTP
	 */
	public String getPassJOptionPane(){
		JPasswordField jpassword = new JPasswordField();   
        if (JOptionPane.showConfirmDialog (null, jpassword, "Entre com a senha", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {   
        	this.pass = String.valueOf( jpassword.getPassword() );   
        } else
        	this.pass = null;
        return this.pass;
	}
	
	/**
	 * Método estatico para verificar o tamanho do arquivo existente no FTP
	 * 
	 * @param client -> contendo a conexao a ser utilizada
	 * @param fileName  -> nome e local do arquivo
	 * 
	 * @return long com os bytes do arquivo
	 */
	public static long getFileSize(FTPClient client, String fileName)
	{
		long fileSize = 0;
		try {
			fileSize = Long.parseLong( client.getSize( fileName ) );
		} catch (IllegalStateException e) {
			logger.error("IllegalStateException");
			logger.error("UtilFTP.getFileSize('" + client.toString() + "', '" + fileName + "')\nIllegalStateException: " + e.getMessage());
			return -1;
		} catch (IOException e) {
			logger.error("IOException");
			logger.error("UtilFTP.getFileSize('" + client.toString() + "', '" + fileName + "')\nIOException: " + e.getMessage());
			return -1;
		}
		return fileSize;
	}

	/**
	 * Método para verificar o tamanho do arquivo existente no FTP
	 * 
	 * @param fileName
	 * 
	 * @return long com os bytes do arquivo
	 */
	public long getFileSize(String fileName)
	{
		long retorno = 0;
		try {
			retorno = Long.parseLong( client.getSize( fileName ) );
		} catch (IllegalStateException e) {
			logger.error("IllegalStateException");
			logger.error("UtilFTP.getFileSize('" + fileName + "')\nIllegalStateException: " + e.getMessage());
			return -1;
		} catch (IOException e) {
			logger.error("IOException");
			logger.error("UtilFTP.getFileSize('" + fileName + "')\nIOException: " + e.getMessage());
			return -1;
		}
		return retorno;
	}

	/**
	 * Método para fazer o Upload de um arquivo local ao FTP
	 * 
	 * @param localFile Endereço local do arquivo
	 */
	public boolean putFileTXT(String localFile) {
		InputStream input;
		try {
			input = new FileInputStream(localFile);
			client.storeFile(localFile, input);
			input.close();
		} catch (Exception e) {
			logger.error("Exception");
			logger.error("UtilFTP.putFileTXT('" + localFile + "')\nException: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Método para fazer o Download de um arquivo do FTP
	 * 
	 * @param localFile Local
	 * @param hostFile Remoto
	 * 
	 * @return true/false se o download foi concluido corretamente
	 */
	public boolean getFileTXT(String localFile, String hostFile) {
		OutputStream output = null;
		try {
			output = new FileOutputStream(localFile);
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException");
			logger.error("UtilFTP.getFileTXT('" + localFile + "', '" + hostFile + "')\nFileNotFoundException: " + e.getMessage());
			return false;
		}

		try {
			client.retrieveFile(hostFile, output);
			output.close();
		} catch (IOException e) {
			logger.error("IOException");
			logger.error("UtilFTP.getFileTXT('" + localFile + "', '" + hostFile + "')\nIOException: " + e.getMessage());
			return false;
		}

		return true;
	}
	
	/**
	 * Método para fazer o Download de um arquivo do FTP
	 * 
	 * @param pastaFTP
	 * @param localFile
	 * @param hostFile
	 */
	@Deprecated
	public void getFileTXT(String pastaFTP, String localFile, String hostFile) {
		if( pastaAtualFTP == null ){
			this.changeDirectory(pastaFTP);
			pastaAtualFTP = pastaFTP;
		}else if( !pastaAtualFTP.equalsIgnoreCase( pastaFTP ) ){
			this.changeDirectory(pastaFTP);
			pastaAtualFTP = pastaFTP;
		}
		this.getFileTXT(localFile, hostFile);
	}
	
	/**
	 * Método para excluir um arquivo do FTP
	 * 
	 * @param hostFile
	 * 
	 * @return true/false se conseguiu excluir
	 */
	public boolean delFile(String hostFile) {
		try {
			if(!client.isConnected()) {
				logger.error("Caiu, reconectando" );
				this.connect();
			}
			client.deleteFile(hostFile);
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException");
			logger.error("UtilFTP.delFile('" + hostFile + "')\nFileNotFoundException: " + e.getMessage());
			return false;
		} catch (IOException e) {
			logger.error("IOException");
			logger.error("UtilFTP.delFile('" + hostFile + "')\nIOException: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Método para desconectar do FTP
	 * 
	 * @return true/false se desconectou
	 */
	public boolean disconnect() {
		try {
			client.disconnect();
			client = null;
		} catch (IllegalStateException e) {
			logger.error("IllegalStateException");
			logger.error("UtilFTP.disconnect()\nIllegalStateException: " + e.getMessage());
			return false;
		} catch (IOException e) {
			logger.error("IOException");
			logger.error("UtilFTP.disconnect()\nIOException: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Método para alterar a pasta do FTP
	 * @param directory
	 */
	public boolean changeDirectory(String directory){
		try {
			client.cwd( directory );
			pastaAtualFTP = directory;
		} catch(Exception e){
			logger.error("Exception");
			logger.error("UtilFTP.changeDirectory('" + directory + "')\nException: " + e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "UtilFTPClient{" +
				"client=" + client.toString() +
				", host='" + host + '\'' +
				", user='" + user + '\'' +
				", pass='" + pass + '\'' +
				", port=" + port +
				", protocol='" + protocol + '\'' +
				", pastaAtualFTP='" + pastaAtualFTP + '\'' +
				'}';
	}
}
