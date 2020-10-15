package util;

import util.file.MyFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Autor: Assis Henrique Oliveira Pacheco
 * @version  0.9
 *
 * Classe para registrar todos os logs de situações ocorridas na execução do programa
 * Preparando a solução para executar como um serviço
 *
 */

public class UtilLog {
    public static boolean setLog(String message){
        System.out.println(message);
        return UtilLog.logGenerator("", message);
    }
    public static boolean setLog(String project, String message){
        System.out.println(message);
        return UtilLog.logGenerator(project, message);
    }
    
	private static boolean logGenerator(String project, String message) {
		MyFile file = new MyFile();
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd") ;
		SimpleDateFormat hrFormat = new SimpleDateFormat("HH:mm:ss") ;
		Date date = new Date() ;

		file.setFile(project + "_LOG_" + dtFormat.format(date) + ".CSV");
		file.newLine(hrFormat.format(date) + ";" + message);
		file.saveFile();
		return true;
	}
}
