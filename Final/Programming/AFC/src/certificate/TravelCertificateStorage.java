package certificate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectToMySQL;

// TODO: Auto-generated Javadoc
/**
 * The Class TravelCertificateStorage.
 *
 * @author Pham Huu Tho
 * create connections to the database and perform READ actions that query most common information about every certificates.
 */
public class TravelCertificateStorage {
	 
	/**
	 * Create a connection to the travelling certificate database.
	 *
	 * @return the connection
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private Connection ConnectToTravelCertificateDB() throws ClassNotFoundException, SQLException {
		return ConnectToMySQL.getInformation("travelling_certificate");
	}
	
	/**
	 * Gets the certificate by id.
	 *
	 * @param ID the id
	 * @return the certificate by id
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public TravelCertificateInformation getCertificateById(String ID) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToTravelCertificateDB();
		Statement statement = connection.createStatement();
		String sql = "Select * from certificate_info WHERE id='" + ID +"'";
		ResultSet rs = statement.executeQuery(sql);
		if (rs != null) {
			while(rs.next()) {
				int type = rs.getInt(2);
				connection.close();
				return new TravelCertificateInformation(ID, type);
			}
		}
		connection.close();
		return null;
	}
	
	/**
	 * Gets the type by id.
	 *
	 * @param ID the id
	 * @return the type by id
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public int getTypeById(String ID) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToTravelCertificateDB();
		Statement statement = connection.createStatement();
		String sql = "Select * from certificate_info WHERE id='" + ID +"'";
		ResultSet rs = statement.executeQuery(sql);
		if (rs != null) {
			while(rs.next()) {
				int type = rs.getInt(2);
				connection.close();
				return type;
			}
		}
		connection.close();
		return -1;
	}
}
