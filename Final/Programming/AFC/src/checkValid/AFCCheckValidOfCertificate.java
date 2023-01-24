package checkValid;

import java.sql.SQLException;

/**
 * The Interface AFCCheckValidOfCertificate.
 *
 * @author Pham Huu Tho
 * New certificates with new rules have to implement this interface. These are the only things that those new certificates ever need.
 */
public interface AFCCheckValidOfCertificate {
	
	/**
	 * Checks if is valid enter and return the error message if there's any error.
	 *
	 * @param certificateId the certificate id
	 * @param stationID the station ID
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	String isValidEnter(String certificateId, int stationID) throws ClassNotFoundException, SQLException;
	
	/**
	 * Checks if is valid exit and return the error message if there's any error.
	 *
	 * @param certificateId the certificate id
	 * @param stationID the station ID
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	String isValidExit(String certificateId, int stationID) throws ClassNotFoundException, SQLException;
}
