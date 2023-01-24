package certificate;

import java.sql.SQLException;

/**
 * @author Pham Huu Tho
 * New certificates with new properties will apply this interface. 
 * For example: 24hourcard and OnewayTicket will update its status after performs the "markStation" action. 
 */
public interface CertificateHelper {
	/**
	 * 
	 * Perform actions with the database when user scans a certificate, only be called after the certificate's qualification is checked.
	 * 
	 * @param ID - represents certificate's Id
	 * @param stationID - represents station's Id
	 * @throws ClassNotFoundException - data doesn't exist.
	 * @throws SQLException - data doesn't exist.
	 * 
	 */
	public void markStation(String ID, int stationID) throws ClassNotFoundException, SQLException;
}