/*
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
package util.MS;

import util.UtilArray;
import util.UtilLog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Access {
	private static final Logger LOGGER= Logger.getLogger(Access.class.getName());

	private Connection connection;
	private Statement statement;
	private String bdName;
	private String strConnection;

	public Access() {
		this.connection = null;
		this.statement = null;
		this.bdName = null;
		this.strConnection = null;
	}

	@Override
	public String toString(){
		return "DB name: " + this.bdName +
				", String connection: " + this.strConnection +
				", Connection: " + connection.toString() +
				", Statement: " + statement.toString();
	}
	
	/**
	 * 
	 * @param nameConnection -> something like that: Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + myAccessLocation
	 */
	public void connect(String nameConnection){
		this.connectJDBC(nameConnection);
	}

	/**
	 *
	 * @param nomeBanco
	 */
	public void connectJDBC(String nomeBanco) {
        try {
            // Efetuando a conexão
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection("jdbc:ucanaccess://" + nomeBanco + ";memory=false", "", "");
        } catch (Exception Driver) {
        	Driver.printStackTrace();
        }
	}
	
	public void close()
	{
	    try
	    {
	        connection.close();
	    }
	    catch (SQLException sqle)
	    {
			UtilLog.setLog("UtilAccess.close():  " + this.bdName);
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
			statement = connection.createStatement();
			statement.execute("DELETE * FROM " + tabela + ";");
		} catch (SQLException e) {
			UtilLog.setLog("UtilAccess.clearTable(" + tabela + "): " + this.bdName);
			e.printStackTrace();
		}
	}
	
	public void updateTable( String sql )
	{
		try {
			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement( sql );
			pstmt.executeUpdate();
		} catch (SQLException e) {
			UtilLog.setLog("UtilAccess.updateTable (" + sql + "): " + this.bdName);

			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param sql
	 * @param parameters
	 */
	public void updateTable( String sql, UtilArray parameters){
		try {
			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement( sql );
			for(int i = 1; i <= parameters.lenght(); i++){
				pstmt.setString(i, parameters.getItemString( i - 1 ));
			}
			pstmt.executeUpdate(); 
		} catch (SQLException e) {
			UtilLog.setLog("UtilAccess.updateTable ('" + sql + "', '"+ parameters + "'):" + this.bdName);
			e.printStackTrace();
		}
	}
		
	public ResultSet executeSQL(String sql) {
		ResultSet rs = null;
		Statement stm = null;
		try {
			stm = connection.createStatement();
			if(sql.toUpperCase().startsWith("DELETE")){
				stm.execute( sql );
			}else{
				rs = stm.executeQuery( sql );
			}
			//rs = stm.executeQuery( SQL );
		} catch (SQLException e) {
			UtilLog.setLog("UtilAccess.executeSQL (" + sql + "): " + this.bdName );
			e.printStackTrace();
		}
		return rs;
	}
}