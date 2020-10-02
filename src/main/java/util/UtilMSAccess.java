/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 * 
 * @version: 1.0
 * 
 * # PortuguesBR
 * 
 * 
 * # English
 * 
 * 
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilMSAccess {
	private Connection con;
	private Statement stm;
	private String nomeBanco;
	private String strConexao;
	
	/**
	 * 
	 * @param nameConnection -> something like that: Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + myAccessLocation
	 */
	public void connect(String nameConnection){
		this.connectJDBC(nameConnection);
	}

	public void connectJDBC(String nomeBanco) {
        try {
            // Efetuando a conexão
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection("jdbc:ucanaccess://" + nomeBanco + ";memory=false", "", "");
        } catch (Exception Driver) {
        	Driver.printStackTrace();
        }
	}
	
	public void close()
	{
	    try
	    {
	        con.close();
	    }
	    catch (SQLException sqle)
	    {
			UtilLog.setLog("UtilAccess.close():  " + this.nomeBanco);
	    	sqle.printStackTrace();
	    }
	}
	
	public String replaceSpacialCharsForSQLAccess( String sql ){
		sql = sql.replaceAll("\"", "'");
		sql = sql.replaceAll("\\*'", "%'");
		return sql;
	}

	public void clearTable(String tabela)
	{
		try {
			stm = con.createStatement();
			stm.execute("DELETE * FROM " + tabela + ";");
		} catch (SQLException e) {
			UtilLog.setLog("UtilAccess.clearTable(" + tabela + "): " + this.nomeBanco);
			e.printStackTrace();
		}
	}
	
	public void updateTable( String sql )
	{
		try {
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement( sql );
			pstmt.executeUpdate();
		} catch (SQLException e) {
			UtilLog.setLog("UtilAccess.updateTable (" + sql + "): " + this.nomeBanco);

			e.printStackTrace();
		}
	}
	
	public void updateTable( String sql, UtilArray parametros){
		try {
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement( sql );
			for(int i = 1; i <= parametros.lenght(); i++){
				pstmt.setString(i, parametros.getItemString( i - 1 )); 
			}
			pstmt.executeUpdate(); 
		} catch (SQLException e) {
			UtilLog.setLog("UtilAccess.updateTable ('" + sql + "', '"+ parametros + "'):" + this.nomeBanco);
			e.printStackTrace();
		}
	}
		
	public ResultSet executeSQL(String sql) {
		ResultSet rs = null;
		Statement stm = null;
		try {
			stm = con.createStatement();
			if(sql.toUpperCase().startsWith("DELETE")){
				stm.execute( sql );
			}else{
				rs = stm.executeQuery( sql );
			}
			//rs = stm.executeQuery( SQL );
		} catch (SQLException e) {
			UtilLog.setLog("UtilAccess.executeSQL (" + sql + "): " + this.nomeBanco );
			e.printStackTrace();
		}
		System.gc();
		return rs;
	}
}
