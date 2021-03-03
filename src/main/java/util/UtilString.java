/**
 * encode: utf-8
 * 
 * Classe utilitaria para trabalhar com Strings
 *
 * Fonte: https://commons.apache.org/proper/commons-text/
 *
 * @autor Assis Henrique Oliveira Pacheco
 * 
 * TODO:
 * - Pattern all methods in english version
 * - Translate all docs to english
 * 
 * @version 1.3
 */
package util;

import java.util.ArrayList;
import java.util.List;
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
	public static int getColumnPosition(String columnToSearch, List<String> dataForSearch, String separador) {
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
						).replaceAll("[^0-9A-Za-z ÁÀÄÂÃáàäâãÉÈËÊéèëêÍÌÏÎíìïîÓÒÖÕÔóòöõôÚÙÜÛúùüûÇç\t;,._|]", ""
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
	 * Método que recebe um texto e transforma em um List de acordo com o separador
	 * 
	 * @param texto
	 * @param separador
	 * 
	 * @return
	 */
	public static List<String> getListFromTextArea(String texto, String separador){
		//Ajustes iniciais
		if(separador.equals(";")) {
			texto = texto.replaceAll("&AMP;", " ");
			texto = texto.replaceAll("#39;", "]");
		}
		
		int textQualifier = 0;
		
		List<String> list = new ArrayList<String>();
		
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
		if(txt_parcial.isEmpty()) {
			// do nothing
		}else{
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
		CharSequence txtOld = new StringBuffer(text);
		StringBuilder txtTemp = new StringBuilder();

		for(int i=0; i<txtOld.length(); i++) {
			if(getHexCodeFromChar(txtOld.charAt(i)).equals("0027")){
				txtTemp.append("\\\'");
			}else{
				txtTemp.append(txtOld.charAt(i));
			}
		}
		return txtTemp.toString();
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
		}else if( ! text.isEmpty() ){
			text = text.replaceAll("'", "");
			text = text.replaceAll("\"", "");
			text = text.replaceAll("`", "");
			
			if( text.lastIndexOf("/") >= text.length()-2 ){
				text += " ";
			}
			if( text.lastIndexOf("\\") >= text.length()-2 ){
				text += " ";
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
		if(text.isEmpty()){
			// do nothing
		}else{
			text = text.replaceAll("\\{", "");
			text = text.replaceAll("}", "");
		}
		return text;
	}

	/**
	 * Método para contar a ocorrencia de um determinado char no texto
	 *
	 * @param text
	 * @param testChar
	 *
	 * @return -> count de quantas ocorrencias foram identificadas
	 */
	public static int countChars(CharSequence text, CharSequence testChar) {

		String rgxFnd = "[^" + testChar + "]*" + testChar;

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
	 * @param string1
	 * @param string2
	 *
	 * @return double -> contento o indice Jaccard
	 *
	 * @since 1.1
	 */
	public static double similarityIndex(CharSequence string1, CharSequence string2){
		org.apache.commons.text.similarity.JaccardSimilarity jaccardSimilarity = new org.apache.commons.text.similarity.JaccardSimilarity();
		return jaccardSimilarity.apply(string1, string2);
	}

	@Override
	public String toString() {
		return "UtilString{" +
				"string='" + string + '\'' +
				'}';
	}
}
