/**
 * encode: utf-8
 * 
 * Classe com métodos para trabalhar com arquivos e diretorios
 *
 * @autor Assis Henrique Oliveira Pacheco
 * @version 1.0
 *
 * TODO: Ajustar para construtor receber nome de arquivo com caminho completo
 * Atual: Recebe um diretorio
 *
 * @changes from 0.9
 *
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilFile {
	private String encode = "UTF-8";
	private FileInputStream arquivoLido;
	private BufferedReader reader;
	protected OutputStreamWriter bufferOut;

	private LocalFile file;
	
    /**
     * Construtor que recebe um objeto LocalFile
     * @param LocalFile
     */
	public UtilFile(LocalFile file){
		this.file = file;
	}
	
	 /**
     * Construtor que recebe uma string LocalFile
     * @param arquivo
     */
	public UtilFile(String file){
		this.file = new LocalFile(file);
	}

	/**
	 * Construtor sem parametros
	 */
	public UtilFile(){
		this.file = new LocalFile();
	}

	/**
	 * Método para setar o nome do arquivo
	 *
	 * @param arquivo -> com o nome e endereço do arquivo
	 */
	public void setArquivo(String file) {
		this.file = new LocalFile(file);
	}
	
	/**
	 * Método para setar o nome do diretorio
	 * @param diretorio
	 */
	public void setDirectory(String directory) {
		this.file.setDirectory(directory);
	}

	/**
	 * Método para criar um arquivo texto, considerando a codificação padrão (UTF-8 ou outra definida em setEncode())
	 * @param nomeArquivo
	 */
	public void createFile(String nomeArquivo){
		this.file.setFileName(nomeArquivo);
		this.createFile();
	}

	/**
	 * Método para criar um arquivo texto, considerando a codificação padrão (UTF-8 ou outra definida em setEncode())
	 */
	public void createFile(){
		try {
			bufferOut = new OutputStreamWriter( new FileOutputStream(this.file.getFileFullName()),encode);
		} catch (IOException ex) {
			Logger.getLogger(UtilFile.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

    /**
	 * Método que exclui um arquivo
	 * @param arquivo Nome do arquivo a ser excluindo, incluindo o diretorio dele
	 * @return boolean informando se foi ou não excluido
	 */
    public boolean excludeFile(String file){
    	return new File(file).delete();
    }

	public boolean delFile(String file){
		return this.excludeFile(file);
	}
    
	/**
	 * Método que exclui um arquivo
	 * @param arquivo Nome do arquivo a ser excluindo, incluindo o diretorio dele
	 * @return boolean informando se foi ou não excluido
	 */
	public boolean excludeFile(File arquivo){
		return arquivo.delete();
	}

    /**
     * Método para salvar e fechar o arquivo
     */
    public void saveFile(){
        try {
        	bufferOut.close();
        } catch (IOException ex) {
            Logger.getLogger(UtilFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para incluir uma linha no arquivo texto
     * @param linha
     */
    public void newLine(String linha){
        try {
        	bufferOut.write( linha );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Método para renomear um arquivo
	 * 
	 * @param newName
	 * 
	 * @return
	 */
	public boolean renameFile(String newName){
		//	 Arquivo ou diretório com nome antigo
		File file = new File( this.file.getFileFullName() );
		// Arquivo ou diretório com novo nome
		File file2 = new File( this.file.getDirectory() + newName );
		// Renomeando arquivo ou diretório
		boolean success = file.renameTo( file2 );
		if (!success) {
			if( file.exists() )
				file2.delete();
			success = file.renameTo(file2);
		}

		file = null;
		file2 = null;
		return success;
	}

	/**
	 * Verifica se um arquivo existe
	 * @return True/False -> se o arquivo existe
	 */
	public boolean isFile(){
		return new File( this.file.getFileFullName() ).isFile();
	}

	/**
	 * Verifica se um arquivo ou pasta existe
	 * @return True/False se o arquivo ou pasta existe
	 */
	public boolean isExiste(){
		return new File( this.file.getFileFullName() ).exists();
	}
	
	/**
	 * Método para criar um diretorio
	 * @param diretorio
	 * @return True/False se pasta foi criada
	 */
	public boolean createDirectory(String diretorio){
		return new File( diretorio ).mkdir();
	}

	/**
	 * Método para criar um diretorio, caso ele não exista
	 * @param directory
	 */
	public void createDirectoryIfDoesntExists(String directory) {
		File path = new File( directory ); //( nmPath ).exists();
		if ( !path.exists() ) {
			path.mkdir();
			//System.out.println("(foi criado uma pasta '" + directory + "')");
		}
	}

	/**
	 * Método para retornar a data de modificação do arquivo no formato long
	 * 
	 * @return long com o tamanho do arquivo
	 */
	public long getDataModificacaoArquivoLong(){
		File file = new File( this.file.getFileFullName() );
		
		return file.lastModified();
	}
	
	/**
	 * Método para retornar data de modificação do arquivo
	 * 
	 * @return GregorianCalendar
	 */
	public GregorianCalendar getFileLastModified(){
		File file = new File(this.file.getFileFullName());
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(file.lastModified());
		
		return calendar;
	}
	
    /**
     * Lista arquivos existentes no diretorio setado
     * Armazena na memoria essa lista para posterior consulta
     */
	public void listFilesInDirectory(){
		File diretorio = new File(this.file.getDirectory());
		File fList[] = diretorio.listFiles();

		System.out.println("Numero de arquivos no diretorio : " + fList.length );

		for ( int i = 0; i < fList.length ; i++ ){ 
			System.out.println( fList[i] );
		}
	}
		
	/**
	 * Método que verifica se o arquivo inicia com UTF8-Boom
	 * 
	 * @return
	 */
	private boolean isFilestartsWithBOM() {
		File textFile = new File(this.file.getFileFullName());
		boolean result = false;
		int[] BYTE_ORDER_MARK = {239, 187, 191};
		
	    if(textFile.length() < BYTE_ORDER_MARK.length) return false;
	    //open as bytes here, not characters
	    int[] firstFewBytes = new int[BYTE_ORDER_MARK.length];
	    InputStream input = null;
	    try {
	      input = new FileInputStream(textFile);
	      for(int index = 0; index < BYTE_ORDER_MARK.length; ++index){
	        firstFewBytes[index] = input.read(); //read a single byte
	      }
	      result = Arrays.equals(firstFewBytes, BYTE_ORDER_MARK);
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    finally {
	    	try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return result;
	}

	/**
	 * Método para ler um arquivo texto, passando a codificação
	 * 
	 * @param encode
	 */
	public void openTextFile(String encode){
		try {
			arquivoLido = new FileInputStream( this.file.getFileFullName() );
			InputStreamReader streamReader = new InputStreamReader(arquivoLido, encode);
			reader = new BufferedReader(streamReader);
		} catch (FileNotFoundException e) {
			System.out.println("Deu erro para abrir o arquivo....");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para abrir um arquivo em formato texto
	 * @param arquivo File
	 */
	public void openTextFile(){
		this.openTextFile(encode);
	}
	
	
	/**
	 * Método para liberar arquivo
	 */
	public void closeTextFile() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		arquivoLido = null;
		reader = null;
	}
	
	/**
	 * Método para ler a proxima linha de um arquivo texto
	 * @return String com a linha
	 */
	public String getNextLineFromFile(){
		String line = null;   
		try {
			if(reader == null) {
				return null;
			}else {
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	/**
	 * Método para copiar arquivo de um local para outro
	 * TODO: igualar lógica ao método copiarArquivo(ArquivoLocal,ArquivoLocal)
	 *
	 * @param arquivoDe
	 * @param arquivoPara
	 */
	public void copyFile( String arquivoDe, String arquivoPara ){
		FileChannel fileDe;
		FileChannel filePara;
		try {
			fileDe = new FileInputStream( arquivoDe ).getChannel();
			filePara = new FileOutputStream( arquivoPara ).getChannel();

			ByteBuffer bb = ByteBuffer.allocateDirect(2048);
			bb.clear();
			while(fileDe.read(bb) != -1) {
				bb.flip();
				filePara.write(bb);
				bb.clear();
			}

			fileDe.close();
			filePara.close();

			File arquivoOrigem = new File ( arquivoDe );
			File arquivoCopiado = new File( arquivoPara );
			GregorianCalendar dataArquivo = new GregorianCalendar();
			dataArquivo.setTimeInMillis(  arquivoOrigem.lastModified() );

			arquivoCopiado.setLastModified( dataArquivo.getTimeInMillis() );

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não localizado: " + arquivoDe);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the encode
	 */
	public String getEncode() {
		return encode;
	}

	/**
	 * @param encode the encode to set
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}
}
