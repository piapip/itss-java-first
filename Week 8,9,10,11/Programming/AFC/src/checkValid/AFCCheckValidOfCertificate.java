package checkValid;

import java.sql.SQLException;

/**
 * @author thovi
 * New certificates with new rules have to implement this interface. These are the only things that those new certificates ever need.
 */
public interface AFCCheckValidOfCertificate {
	String isValidEnter(String certificateId, int stationID) throws ClassNotFoundException, SQLException;
	String isValidExit(String certificateId, int stationID) throws ClassNotFoundException, SQLException;
}
