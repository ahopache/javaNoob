/**
 * encode: UTF-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 * @version: 0.9
 */
package util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Classe para trabalhar com array/listas
 */
public class UtilArray {
	List<Object> listaArray = new Vector<Object>();
	
	public UtilArray(){}


	public void newItem(String newItem){
		listaArray.add( newItem );
	}

	
	public String getItemString(int posicao){
		try{
			return listaArray.get( posicao ).toString();
		}catch(java.lang.IndexOutOfBoundsException e){
			System.out.println("Não foi possivel localizar a posição escolhida!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";	 
	}
	
	public Object getItemObject(int posicao){
		return listaArray.get( posicao );
	}
	
	public int lenght(){
		return listaArray.size();
	}
	
	/**
	 * Método que transforma um String em uma lista
	 * 
	 * TODO: Descobrir como funcionar se o separador tiver mais de um caracter (BUG)
	 * 
	 * @param texto
	 * @param separador
	 * @return
	 */
	public static ArrayList<String> getListFromTextArea(String text, String separador){
		String textoNormalizado = text;

		try{
			if(separador.length() > 1) {
				text = text.replaceAll("\t" , " "); //ReplaceAll -> Substitui char por um regex
				text = text.replaceAll("\r" , "");
				text = text.replaceAll("\n" , " ");
				text = text.replace("  " , " ");//Replace -> Substitui substring por outro
				if( text.indexOf("\t") < 0) {
					text = text.replace(separador , "\t");
					separador = "\t";
					UtilLog.setLog("UtilString.getListFromTextArea('" + text + "', '" + separador + "') - Ajustei o separador para tab!");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		ArrayList<String> list = new ArrayList<String>();
		if( text.indexOf( separador + separador + "") >= 0) {
			textoNormalizado = text.replaceAll(separador + separador, "" + separador + "''" + separador + "");
			while (textoNormalizado.indexOf(separador + separador) >0) {
				textoNormalizado = textoNormalizado.replaceAll(separador + separador, "" + separador + "''" + separador + "");
			}
			if(textoNormalizado.startsWith( separador )) {
				textoNormalizado = "." + textoNormalizado;
			}

			StringTokenizer tokens = new StringTokenizer( textoNormalizado, separador );
			while(tokens.hasMoreTokens()){
				list.add((String)tokens.nextElement());
			}
		} else {
			if(textoNormalizado.startsWith( separador ))
				System.out.println("Começa com: " + separador);

			StringTokenizer tokens = new StringTokenizer( text, separador );
			while(tokens.hasMoreTokens()){
				list.add((String)tokens.nextElement());
			}
		}
		
		return list;
	}

	/**
	 * Método que transforma um ResultSet em uma lista
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
    public static List<String[]> converteResultSetEmList( ResultSet rs ) throws SQLException{
        ResultSetMetaData rsmd = rs.getMetaData();
        int qtdColunas = rsmd.getColumnCount();    // numero de colunas   
        List<String[]> ret = new ArrayList<String[]>();    // lista de "linhas"   

        while(rs.next()){
			String[] linha = new String[qtdColunas];
			for(int i = 0; i < qtdColunas; i++){
				linha[i] = rs.getString( i + 1 );
				if( linha[i] == null){
					linha[i] = "";
				}
			}
			ret.add(linha);
		}
        
        return ret;   
    }
}