/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 * @version: 0.9
 * 
 * TODO:
 * - Padronizar documentações em ingles/portugues
 * - Padronizar LOG usando UtilLog
 * 
 */
package util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Zip {
    private static final int TAMANHO_BUFFER = 2048; // 2 Kb

	public Zip() { }

    /**
     * Método para verificar se o arquivo é do tipo GZip
     * 
     * @param f File referente ao arquivo a ser testado
     * 
     * @return boolean
     */
    public static boolean isGZipped(File f) {
    	int magic = 0;
    	try {
    		RandomAccessFile raf = new RandomAccessFile(f, "r");
    		magic = raf.read() & 0xff | ((raf.read() << 8) & 0xff00);
    		raf.close();
    	} catch (Throwable e) {
    		e.printStackTrace(System.err);
    	}
    	return magic == GZIPInputStream.GZIP_MAGIC;
    }
	
    public String getFileNameInsideZIP(File arquivoZip){
        ZipFile zip = null;
        try {
            zip = new ZipFile( arquivoZip );
            return zip.getName();
        } catch (ZipException ex) {
            Logger.getLogger(Zip.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Zip.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Método para extrair um arquivo ZIP
     * 
     * @param arquivoZip a ser extraido
     * @param diretorio a ser salvo o conteudo do ZIP
     */
    public void extractZip( String arquivoZip, String diretorio ) {
    	try {
			this.extractZip( new File (arquivoZip), new File (diretorio) );
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void extractZip( File arquivoZip, File diretorio ) throws IOException {   
        ZipFile zip = null;
        File arquivo = null;
        InputStream is = null;   
        OutputStream os = null;   
        byte[] buffer = new byte[TAMANHO_BUFFER];   
        try {
            //cria diretório informado, caso não exista   
            if( !diretorio.exists() ) {
                diretorio.mkdirs();   
            }   
            if( !diretorio.exists() || !diretorio.isDirectory() ) {
                throw new IOException("Informe um diretório válido");   
            }   
            zip = new ZipFile( arquivoZip );
            Enumeration e = zip.entries();   
            while( e.hasMoreElements() ) {
                ZipEntry entrada = (ZipEntry) e.nextElement();   
                arquivo = new File( diretorio, entrada.getName() );   
                //se for diretório inexistente, cria a estrutura    
                //e pula pra próxima entrada   
                if( entrada.isDirectory() && !arquivo.exists() ) {   
                    arquivo.mkdirs();   
                    continue;   
                }   
                //se a estrutura de diretórios não existe, cria   
                if( !arquivo.getParentFile().exists() ) {   
                    arquivo.getParentFile().mkdirs();   
                }
                try {
                    //lê o arquivo do zip e grava em disco
                    is = zip.getInputStream( entrada );
                    os = new FileOutputStream( arquivo );
                    int bytesLidos = 0;
                    if( is == null ) {
                        throw new ZipException("Erro ao ler a entrada do zip: "+entrada.getName());
                    }
                    while( (bytesLidos = is.read( buffer )) > 0 ) {
                        os.write( buffer, 0, bytesLidos );
                    }
                } catch( ZipException e1){
                	System.out.println("Erro ao descompactar " + arquivoZip);
                } finally {
                    if( is != null ) {
                        try {
                            is.close();
                        } catch( Exception ex ) {}
                    }
                    if( os != null ) {
                        try {
                            os.close();
                        } catch( Exception ex ) {}
                    }
                }
            }
        } finally {
        	//System.out.println("Erro no arquivo:" + arquivoZip.getName());
        	if( zip != null ) {
                try {
                    zip.close();
                } catch( Exception e ) {}
            }
        }
    }
}