package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Clase de Acceso a Base de datos, abstracta, que permite
 * hacer de forma simple y sin preocuparse de la sintaxis SQL, las
 * operaciones CRUD sobre una bd.
 * @author Miguel Páramos
 *
 */
public abstract class DAO {
	
	/** objeto conexión, desde el que se va a referenciar a la BD. la operativa será
	 * conectar, usar, y desconectar lo antes posible.
	 */
	private static Connection connection;

	/**
	 * Función privada que abre una conexión con un servidor de base de datos.
	 * Las propiedades de la base de datos deben estar definidas en un fichero 
	 * ./bdconfig.ini con el siguiente formato:
	 * 		1º Linea: ip o dns del servidor
	 * 		2º Linea: puerto
	 * 		3º Linea: nombre bd
	 * 		4º Linea: usuario BD
	 * 		5º Linea: contraseña BD
	 * @return statement listo para hacer la consulta que necesitemos
	 */
	private static Statement connect() {
		try {
			BufferedReader lector=new BufferedReader(new FileReader("./bdconfig.ini"));
			String ip=lector.readLine();
			int puerto=Integer.parseInt(lector.readLine());
			String nombreBD=lector.readLine();
			String usuario=lector.readLine();
			String password=lector.readLine();
			lector.close();
			connection = DriverManager.getConnection(
					"jdbc:mysql://"+ip+":"+puerto+"/"+nombreBD, 
					usuario, password);
			return connection.createStatement();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	
	public static int insert(String table,HashMap<String,String> campos) throws SQLException {
		Statement querier=connect();
		String query="insert into "+table+" (";
		Iterator it=campos.keySet().iterator();
		while(it.hasNext()) {
			String clave=(String)it.next();
			query+=clave+",";
		}
		query=query.substring(0,query.length()-1)+") values (";
		if(Config.verboseMode) {
			System.out.println(query);
		}
		int ret=querier.executeUpdate(query);
		disconnect(querier);
		return ret;
	}
	
	public static int delete(String query) throws SQLException{
		Statement querier=connect();
		if(Config.verboseMode) {
			System.out.println(query);
		}
		int ret=querier.executeUpdate(query);
		disconnect(querier);
		return ret;
	}
	
	

	/**
	 * función privada que cierra en su interior tanto el statement pasado por argumentos
	 * como la conexión, para dejar libre la entrada a más programas que quieran conectar
	 * a la misma bd
	 * @param smt Statement que se va a liberar (cerrar).
	 */
	private static void disconnect(Statement smt) {
		try {
			smt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
