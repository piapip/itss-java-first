package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import certificate.Config;

// TODO: Auto-generated Javadoc
/**
 * @author Pham Huu Tho 
 * The Class ConnectToMySQL.
 */
public class ConnectToMySQL {
	
	/**
	 * Gets the information of the database from the Config file.
	 *
	 * @param dbName the db name
	 * @return the information
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Connection getInformation(String dbName) throws SQLException, ClassNotFoundException {
		String hostName = "localhost";
		String userName = Config.dbUsername;
		String password = Config.dbPassword;
		return getInformation(hostName, dbName, userName, password);
	}
	
	/**
	 * Return the connection to the database with the corresponding information.
	 *
	 * @param hostName the host name
	 * @param dbName the db name
	 * @param userName the user name
	 * @param password the password
	 * @return the information
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Connection getInformation(String hostName, String dbName, String userName, String password) throws SQLException, ClassNotFoundException {
		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}

}
