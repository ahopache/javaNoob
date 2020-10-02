/**
 * encode: utf-8
 * 
 * Classe utilitaria para trabalhar com Strings
 *
 * Fonte: https://commons.apache.org/proper/commons-text/
 *
 * @autor Assis Henrique Oliveira Pacheco
 * @version 1.2
 * Incluido construtor recebendo String
 *
 * @changes from 1.1
 * Incluido método: int getColumnPosition(String columnToSearch, String dataForSearch, String separador)
 *
 * @changes from 1.0
 * Incluido método: double similarityIndex(String s1, String s2)
 *  - Esse método compara duas strings e retorna o Jaccard index: https://en.wikipedia.org/wiki/Jaccard_index
 *  - Atraves desse indice, é possivel saber se as strings são semelhantes
 *
 * Removivo método @deprecated: ArrayList<String> getListFromTextAreaV1(String texto, String separador)
 *
 * Melhorias gerais na documentação dos métodos
 */
package util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilString {
	private String string;

	/**
	 * construtor recebendo a String
	 * 
	 * @param string
	 */
	public UtilString(String string){
		this.string = string;
	}

	/**
	 * construtor vazio
	 */
	public UtilString(){

	}

	/**
	 * Método que retorna a posição que um texto se encontra em um texto separado por um separador
	 *
	 * @param columnToSearch ->
	 * @param dataForSearch -> linha contendo os dados
	 * @param separador -> qual caracter corresponde a separação de campos
	 *
	 * @return
	 */
	public static int getColumnPosition(String columnToSearch, ArrayList<String> dataForSearch, String separador) {
		int posicao;
		boolean found = false;

		for(posicao = dataForSearch.size() - 1; posicao >= 0; --posicao) {
			if ((dataForSearch.get(posicao)).equalsIgnoreCase(columnToSearch)) {
				found = true;
				break;
			}
		}
		if(!found){
			for(posicao = dataForSearch.size() - 1; posicao >= 0; --posicao) {
				if (
						(dataForSearch.get(posicao)
						).replaceAll("[^0-9A-Za-z ÁÀÄÂÃáàäâãÉÈËÊéèëêÍÌÏÎíìïîÓÒÖÕÔóòöõôÚÙÜÛúùüûÇç \t;,._|]", ""
						).equalsIgnoreCase(columnToSearch)
				) {
					found = true;
					break;
				}
			}
		}

		return found ? posicao : -1;
	}

	/**
	 * Método que recebe um texto e transforma em um ArrayList de acordo com o separador
	 * 
	 * @param texto
	 * @param separador
	 * 
	 * @return
	 */
	public static ArrayList<String> getListFromTextArea(String texto, String separador){
		//Ajustes iniciais
		if(separador.equals(";")) {
			texto = texto.replaceAll("&AMP;", " ");
			texto = texto.replaceAll("#39;", "]");
		}
		
		int textQualifier = 0;
		
		ArrayList<String> list = new ArrayList<String>();
		
		StringBuffer txt = new StringBuffer(texto);
		StringBuffer txt_parcial = new StringBuffer();
		for(int i = 0;i<txt.length();i++) {
			if(textQualifier == 0) {
				if(txt.substring(i, i+1).equals("\"")) {
					textQualifier = 1;
				}else if(txt.substring(i, i+1).equalsIgnoreCase(separador)) {
					list.add(txt_parcial.toString().trim());
					txt_parcial = new StringBuffer();
				}else {
					txt_parcial.append(txt.substring(i, i+1));
				}
			}else {
				if(txt.substring(i, i+1).equals("\"")) {
					textQualifier = 0;
				}else {
					txt_parcial.append(txt.substring(i, i+1));
				}
			}
		}
		if(txt_parcial.length()>0) {
			list.add(txt_parcial.toString().trim());
		}
		
		return list;
	}

	/**
	 * Método para remover caracteres especiais"
	 *
	 * @param text -> String contendo o texto original
	 * @return -> String sem caracter especial
	 */
	@Deprecated
	public static String removeCaracterEspecial(String text){
		text = text.replaceAll("\"", "");

		return text;
	}

	/**
	 * Scape special chars for insert into DB
	 * 
	 * @param text
	 * 
	 * @return
	 */
	public static String scapeSpecialCharForInsert(String text){
		StringBuffer txt = new StringBuffer(text);
		StringBuilder txt_temp = new StringBuilder();

		for(int i=0; i<txt.length(); i++) {
			if(getHexCodeFromChar(txt.charAt(i)).equals("0027")){
				txt_temp.append("\\\'");
			}else{
				txt_temp.append(txt.charAt(i));
			}
		}
		return txt_temp.toString();
	}

	/**
	 * Método para remover as aspas: ', ", `
	 *
	 * @param text -> String contendo o texto original
	 * 
	 * @return -> String sem caracter especial
	 */
	public static String removeAspas(String text){
		if(text == null) {
			return null;
		}else if( text.length() > 0 ){
			text = text.replaceAll("'", "");
			text = text.replaceAll("\"", "");
			text = text.replaceAll("`", "");
			
			if( text.lastIndexOf("/") >= text.length()-2 ){
				text = text + " ";
			}
			if( text.lastIndexOf("\\") >= text.length()-2 ){
				text = text + " ";
			}

		}
		return text;
	}

	/**
	 * Método para removar as chaves { } de um texto
	 *
	 * @param text -> String contendo o texto original
	 * @return -> String sem caracter especial
	 */
	public static String removeChaves(String text){
		if( text.length() > 0 ){
			text = text.replaceAll("\\{", "");
			text = text.replaceAll("}", "");
		}
		return text;
	}

	@Deprecated
	public static double contaCaracteres(String texto, String Carater) {
		return countChars(texto, Carater);
	}

	/**
	 * Método para contar a ocorrencia de um determinado char no texto
	 *
	 * @param text
	 * @param test_char
	 *
	 * @return -> count de quantas ocorrencias foram identificadas
	 */
	public static int countChars(String text, String test_char) {

		String rgxFnd = "[^" + test_char + "]*" + test_char;

		Pattern pattern = Pattern.compile(rgxFnd);
		Matcher matcher = pattern.matcher(text);
		int count = 0;
		while (matcher.find()) {
			count++;
		}

		return count;
	}

	/**
	 * Método para testar se a string é um numero
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) { 
		try {  
			Double.parseDouble(str);  
			return true;
		} catch(NumberFormatException e){  
			return false;  
		}  
	}

	/**
	 * Método para remover espaços do inicio e final do texto
	 *
	 * @param texto
	 * 
	 * @return
	 */
	@Deprecated
	public static String rlTrim(String texto){
		return texto.trim();
	}

	/**
	 * Transforma uma letra em unicode
	 *
	 * @param letra
	 * @return
	 */
	public static String geraCodigoUnicode(char letra) {
		String hexa = Integer.toHexString( (int)letra );
		String prefix;
		if( hexa.length() == 1 ) {
			prefix = "\\u000";
		} else if( hexa.length() == 2 ) {
			prefix = "\\u00";
		} else if( hexa.length() == 3 ) {
			prefix = "\\u0";
		} else {
			prefix = "\\u";
		}
		return prefix + hexa;
	}

	/**
	 * Transforma um texto em unicode
	 *
	 * @param string
	 * @return
	 */
	public static String geraCodigoUnicode(String string) {
		char[] temp = string.toCharArray();
		String stringConvertida = "";

		for (int i = 0; i < temp.length; i++) {
			stringConvertida += geraCodigoUnicode( temp[i] );
		}

		return stringConvertida;
	}

	/**
	 * get hex code from char
	 * 
	 * @param letra
	 * 
	 * @return hex code
	 */
	public static String getHexCodeFromChar(char letra) {
		String hexa = Integer.toHexString( (int)letra );
		String prefix;
		if( hexa.length() == 1 ) {
			prefix = "000";
		} else if( hexa.length() == 2 ) {
			prefix = "00";
		} else if( hexa.length() == 3 ) {
			prefix = "0";
		} else {
			prefix = "";
		}
		return prefix + hexa;
	}

	/**
	 * Método para avaliar a similaridade entre dois textos
	 * indice utilizado para comparação: https://en.wikipedia.org/wiki/Jaccard_index
	 *
	 * @param s1
	 * @param s2
	 *
	 * @return double -> contento o indice Jaccard
	 *
	 * @since 1.1
	 */
	public static double similarityIndex(String s1, String s2){
		org.apache.commons.text.similarity.JaccardSimilarity jaccardSimilarity = new org.apache.commons.text.similarity.JaccardSimilarity();
		return jaccardSimilarity.apply(s1, s2);
	}
}
