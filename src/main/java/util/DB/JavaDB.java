/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 * @version: 0.9
 * 
 * TODO:
 * - Padronizar documentações em ingles/portugues
 * - Padronizar LOG usando UtilLog
 */
package util;

import java.io.File;
import java.sql.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.logging.Logger;

public class UtilJavaDB {
	protected static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	protected StringBuilder jdbc = new StringBuilder("jdbc:derby:");
	protected static String _pastaLocal = System.getProperty("user.home") + File.separatorChar + "Documents\\Repositorio\\";
	private UtilArray utilArray;
	
	/**
	 * Método construtor, já cria banco teste se ele não existir
	 */
	public UtilJavaDB(String db) {
		this.createDB(db);
	}

	/**
	 * Método que cria um BD
	 *
	 * @param db -> nome do BD a ser criado, fisicamente é o nome da pasta
	 */
	protected void createDB(String db) {
		try {
			jdbc.append(db);
			Connection conn;
			Class.forName( driver );
			conn = DriverManager.getConnection( new StringBuilder(jdbc).append(";create=true").toString() );

			if(db.equalsIgnoreCase("LOG")) {
				if( !this.checkIfTableExists( "log" ) ) {
					this.createTableWithoutDrop("log", "(data TIMESTAMP,log varchar(255), projeto varchar(255) )");
				}
			}

			conn.close();
		} catch (SQLException e) {
			if(db.equals("LOG")){
				logger.info("Não foi possivel acessar log!");
			}else{
				logger.info("Outro erro: " + e.getMessage());
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Método para enviar ao JavaDB os comandos
	 *
	 * @param sql
	 */
	public void sendCommand(CharSequence sql){
		Connection conn;
		Statement stmt;

		try {
			Class.forName( driver );

			conn = DriverManager.getConnection( jdbc.toString() );

			stmt = conn.createStatement();

			stmt.executeUpdate( sql.toString() );

			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			checkSQLException(sql.toString(), e);
		}
	}
	
	/**
	 * Método para analisar o que ocorreu para que o SQL retornasse erro
	 *
	 * @param sql -> SQL original que resultou em erro
	 * @param e -> Exceção que ocorreu ao executar o código
	 *
	 * O que ele faz:
	 * - Caso seja um insert em lotes, quebra em inserts únicos e reenvia;
	 *
	 * Pendencias:
	 * TODO: Concluir validação de erros, para correção de dados
	 */
	private boolean checkSQLException(String sql, SQLException e) {
		List a;

		if(e.getMessage().equals("The resulting value is outside the range for the data type DECIMAL/NUMERIC(31,0).")) {
			a = UtilArray.getListFromTextArea(sql, ") , (");
			if(a.size() > 1) {
				System.out.print("Corrigindo..." + "");
				System.out.println(a.size());
				String insert_sql = a.get(0).toString();
				for (int i = 0; i < a.size(); i++) {
					if (i == 0) {
						this.sendCommand(a.get(i).toString() + ")");
						insert_sql = a.get(0).toString().substring(0, a.get(0).toString().indexOf(") values (")) + ") values ";
					} else if(i == a.size() - 1){
						System.out.print("");
						System.out.println(",");
						this.sendCommand(insert_sql + "(" + a.get(i));
					} else {
						System.out.print(".");
						this.sendCommand(insert_sql + "(" + a.get(i) + ")");
					}
				}
			}
		}else if(e.getMessage().equals("The resulting value is outside the range for the data type INTEGER.")) {
			System.out.println("Pulei registro com erro");
		}else {
			System.out.println(sql);
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Check if table exists
	 *
	 * @param table -> table to check
	 * @return -> true/false if table exists
	 */
	protected boolean checkIfTableExists(String table) {
		boolean flag = false;
		Connection conn;

		try {
			conn = DriverManager.getConnection( jdbc.toString() );
			DatabaseMetaData meta = conn.getMetaData();
			
			ResultSet res = meta.getTables(null, null, null, new String[]{"TABLE"});

			while (res.next()) {
			    if(res.getString("TABLE_NAME").equalsIgnoreCase(table)) {
			    	flag = true;
			    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * Create table
	 * Obs: If exists, exclude and create
	 *
	 * @param table_name -> table name to create
	 * @param columns -> columns of the table
	 */
	public void createTable(String table_name, String columns) {
		if( this.checkIfTableExists( table_name ) ) {
			System.out.print("Delete table: " + table_name);
			this.sendCommand(new StringBuilder("DROP TABLE ").append(table_name).toString());
		}
		System.out.print("Create table: " + table_name);
		this.sendCommand(new StringBuilder("create table ").append(table_name).append(" ").append(columns).toString());
		System.out.println(".tabela criada!");
	}
	
	/**
	 * Create table
	 *
	 * @param table_name -> table name to create
	 * @param columns -> columns of the table
	 */
	public void createTableWithoutDrop(String table_name, String columns) {
		if( !this.checkIfTableExists( table_name ) ) {
			System.out.print("Create table: " + table_name);
			this.sendCommand(new StringBuilder("create table ").append(table_name).append(" ").append(columns).toString());
			System.out.println("table created!");
		}
	}
	
	/**
	 * Insert line to table
	 *
	 * @param tableName -> tabela onde os registros serão inseridos
	 * @param linha -> linha com os dados para inserir
	 */
	public void insertLine(String tableName, String linha) {
		this.sendCommand( "insert into " + tableName + " values(" + linha + ")" );
	}

	/**
	 * Método para realizar regex
	 * @autor Fagner
	 *
	 * Pendência: Entender se ele deve ficar aqui ou migrar para UtilString
	 *
	 * @param aRegexFragment
	 * @return
	 */
	@Deprecated
	public static String forRegex(String aRegexFragment){
	    final StringBuilder result = new StringBuilder();

	    final CharacterIterator iterator =
	      new StringCharacterIterator(aRegexFragment)
	    ;
	    char character =  iterator.current();
	    while (character != CharacterIterator.DONE ){
	      /*
	       All literals need to have backslashes doubled.
	      */
	      if (character == '.') {
	        result.append("\\.");
	      }
	      else if (character == '\\') {
	        result.append("\\\\");
	      }
	      else if (character == '?') {
	        result.append("\\?");
	      }
	      else if (character == '*') {
	        result.append("\\*");
	      }
	      else if (character == '+') {
	        result.append("\\+");
	      }
	      else if (character == '&') {
	        result.append("\\&");
	      }
	      else if (character == ':') {
	        result.append("\\:");
	      }
	      else if (character == '{') {
	        result.append("\\{");
	      }
	      else if (character == '}') {
	        result.append("\\}");
	      }
	      else if (character == '[') {
	        result.append("\\[");
	      }
	      else if (character == ']') {
	        result.append("\\]");
	      }
	      else if (character == '(') {
	        result.append("\\(");
	      }
	      else if (character == ')') {
	        result.append("\\)");
	      }
	      else if (character == '^') {
	        result.append("\\^");
	      }
	      else if (character == '$') {
	        result.append("\\$");
	      }
	      else {
	        //the char is not a special one
	        //add it to the result as is
	        result.append(character);
	      }
	      character = iterator.next();
	    }
	    return result.toString();
	  }

	
	
	/**
	 * Método para retornar a coluna no modelo para realizar incluir no comando de Insert
	 *
	 * @param dados
	 * @param colunaTXT
	 * @param priLinha
	 * @return
	 */
	protected String getColunaParaInsert(List<String> dados, String colunaTXT, String priLinha, String tipo) {
		return this.getColunaParaInsert(dados,colunaTXT,priLinha,tipo,"\t");
	}
	
	/**
	 * Método para retornar a coluna no modelo para realizar incluir no comando de Insert
	 * @param dados
	 * @param colunaTXT
	 * @param priLinha
	 * @param separador
	 * @return
	 */
	protected String getColunaParaInsert(List<String> dados, String colunaTXT, String priLinha, String tipo, String separador) {
		int posicao = this.getPosicaoColuna(colunaTXT, priLinha,separador);
		String coluna = null;
		
		// TODO Colocar DATE e DATETIME 
		// 02/01/2019 11:36:59

		if (posicao < dados.size()) {
			try {
				coluna = dados.get(posicao);
			}catch (IndexOutOfBoundsException e){
				System.out.println("Não localizei a coluna:" + colunaTXT);
				System.out.println("Em: " + priLinha);
				e.printStackTrace();
				System.exit(1);
			}catch ( Exception e){
				System.out.println("Outro erro");
				e.printStackTrace();
				System.exit(1);
			}

			if(coluna == null)
				return "null";
			else if(coluna.equals(""))
				return "null";
			else if(coluna.toUpperCase().equals("VAZIO"))
				return "null";
			else if(coluna.toUpperCase().equals("NULL"))
				return "null";
			else if(coluna.equals("''"))
				return "null";
			else if(coluna.equals("\"\""))
				return "null";
			else if(tipo.equalsIgnoreCase("int")) {
				coluna = coluna.replaceAll("[^0-9]", "");
				
				if(coluna.length() < 1) {
					return "null";
				}else {
					return coluna;
				}
			}else
				return "'" + coluna + "'";
		}else
			return "null";
	}
	
	/**
	 * Método para limpar caracteres especiais
	 * @param texto -> String contendo os caracteres especiais
	 * @return
	 */
	protected String limpaString(String texto) {
		texto = texto.replaceAll("'", "");
		return texto;
	}
	
	/**
	 * Método para pesquisar qual a posição da coluna dentro do arquivo
	 * @param colunaPesquisar
	 * @param linha
	 * @return
	 */
	protected int getPosicaoColuna(String colunaPesquisar, String linha) {
		return this.getPosicaoColuna(colunaPesquisar, linha, "\t");
	}
	
	/**
	 * Método para pesquisar qual a posição da coluna dentro do arquivo
	 *
	 * @param colunaPesquisar -> nome da coluna que precisa retornar
	 * @param linha -> linha com os dados
	 * @param separador -> Separador de texto utilizado
	 * @return -> o dado existente na coluna
	 */
	private int getPosicaoColuna(String colunaPesquisar, String linha, String separador) {
		int posicao = 0;
		boolean found = false;
		//linha = UtilString.removeAspas(linha);
		//REGEX duplo está aqui para eliminar o erro de resultados iguais retornarem diferente
		linha = linha.replaceAll("[^0-9A-Za-záéíóúÁÉÍÓÚàèìòùÀÈÌÒÙãõÃÕâêîôûÂÊÎÔÛäëïöüÄËÏÖÜ \t;_|]", "");
		colunaPesquisar = colunaPesquisar.replaceAll("[^0-9a-zA-ZáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙãõÃÕâêîôûÂÊÎÔÛäëïöüÄËÏÖÜ \t;_|]", "");
		
		List<String> dados = UtilString.getListFromTextArea(linha, separador);
		for(posicao = dados.size()-1; posicao >= 0; posicao--) {
			if(dados.get(posicao).equalsIgnoreCase(colunaPesquisar)) {
				found = true;
				break;
			}
		}
		if(found)
			return posicao;
		else
			return -1;
	}
	
	
	/**
	 * Método para excluir tabela
	 * @param tableName -> nome da tabela a ser excluida
	 */
	public void dropTable(String tableName) {
		this.sendCommand("drop table " + tableName);
	}
	
	/**
	 * Método que retorna os dados de uma tabela
	 * @param table -> tabela com os dados a serem retornados
	 * @return -> lista com os dados
	 */
	public List<String[]> getListDatabase(String table) {
		utilArray = new UtilArray();
		
		try {
			Class.forName( this.driver );
			Connection conn = DriverManager.getConnection( jdbc.toString() );
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(new StringBuilder("SELECT * FROM ").append(table).toString());
			return utilArray.converteResultSetEmList(rs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
		    if(jdbc.toString().equals("jdbc:derby:LOG")){
                System.out.println("Não foi possivel acessar o LOG de utilJavaDB");
            }else{
                e.printStackTrace();
            }
		}
		return null;
	}
	
	/**
	 * Método que retorna os dados de uma tabela, utilizando um filtro
	 *
	 * @param table -> tabela do banco contendo os dados para retornar
	 * @param where -> filtro a ser aplicado na tabela
	 *
	 * @return -> uma lista com os dados da tabela utilizando o filtro
	 */
	public List<String[]> getListDatabaseWithWhere(String table, String where, String columns) {
		utilArray = new UtilArray();
		
		try {
			Class.forName( this.driver );
			Connection conn = DriverManager.getConnection( jdbc.toString() );
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(new StringBuilder("SELECT ").append(columns).append(" FROM ").append(table).append(" ").append(where).toString());
			return utilArray.converteResultSetEmList(rs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Método para arquivar logs
	 *
	 * @param txt_log -> Mensagem a ser salva
	 * @param txt_projeto -> Nome do projeto que está executando
	 */
	public static void salvaLog(String txt_log, String txt_projeto) {
		boolean flag = false;
		final int limitString = 255;
		int max = txt_log.length();
		if(max >= limitString)
			max = limitString;
		Connection conn;
		Statement stmt;
		
		try {
			Class.forName( driver );
			conn = DriverManager.getConnection( "jdbc:derby:log;create=true" );
			stmt = conn.createStatement();
			
			DatabaseMetaData meta = conn.getMetaData();
			
			ResultSet res = meta.getTables(null, null, null, new String[]{"TABLE"});

			while (res.next()) {
			    if(res.getString("TABLE_NAME").equalsIgnoreCase("log")) {
			    	flag = true;
			    }
			}
			
			if(!flag) {
				stmt.executeUpdate("CREATE TABLE log (data TIMESTAMP,log varchar(255), projeto varchar(255))");
			}
			
			stmt.executeUpdate(
					new StringBuilder("insert into log (data, log, projeto) values (CURRENT_TIMESTAMP,'"
					).append(txt_log.substring(0, max).replaceAll("'", "")
					).append("','"
					).append(txt_projeto).append("')").toString() );
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			if(e.getMessage().contains("Failed to start database 'log' with class loader jdk.internal.loader.ClassLoaders")){
				System.out.println("Continuando....");
			}else{
				System.out.println("Mensagem: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void viewLog() {
		Connection conn;
		Statement stmt;
		ResultSet rs = null;
		
		try {
			Class.forName( driver );

			conn = DriverManager.getConnection( jdbc.toString() );
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("Select * from log");
			
			while (rs.next()) {//Consulta
				System.out.println(
						rs.getString("data") + " - " +
						rs.getString("log")
				);
			}
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}