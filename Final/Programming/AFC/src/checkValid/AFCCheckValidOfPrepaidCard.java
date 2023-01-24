package checkValid;

import certificate.PrepaidCard;
import certificate.PrepaidCardHelper;

import java.sql.SQLException;

import station.*;

// TODO: Auto-generated Javadoc
/**
 * The Class AFCCheckValidOfPrepaidCard.
 *
 * @author Pham Huu Tho
 * Return warning messages if PrepaidCard has any problem with passing the entry.
 */
public class AFCCheckValidOfPrepaidCard extends PrepaidCardHelper implements AFCCheckValidOfCertificate{

	/**
	 * Instantiates a new AFC check valid of prepaid card.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public AFCCheckValidOfPrepaidCard() throws SQLException, ClassNotFoundException {
		super();
	}

	/**
	 * Checks if is valid enter. Return the first error that is found.
	 * If there's no error, return null. 
	 *
	 * @param certificateId the certificate id
	 * @param stationID the station ID
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@Override
	public String isValidEnter(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		String warning = isExist(certificateId);
		if(warning != null) return warning;
		
		warning = exceedBaseFare(certificateId, stationID);
		if(warning != null) return warning;
		
		else return null;
	}
	
	/**
	 * Checks if is valid exit. Return the first error that is found.
	 * If there's no error, return null. 
	 *
	 * @param certificateId the certificate id
	 * @param stationID the station ID
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@Override
	public String isValidExit(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		String warning = isExist(certificateId);
		if(warning != null) return warning; 
		
		warning = hasEnoughMoney(certificateId, stationID);
		if(warning != null) return warning;
		else return null;
	}
	
	/**
	 * Checks if is exist and return corresponding error message if there's any error.
	 *
	 * @param certificateId the certificate id
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private String isExist(String certificateId) throws ClassNotFoundException, SQLException {
		if (getCardById(certificateId) == null) return "Card doesn't exist. Please buy a new one.";
		return null;
	}
	
	/**
	 * Exceed base fare and return corresponding error message if there's any error..
	 *
	 * @param certificateId the certificate id
	 * @param stationID the station ID
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private String exceedBaseFare(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		StationHelper helper = new StationHelper();
		double requirement = helper.getLowestCost(stationID) - getCardById(certificateId).getBalance();
		if(requirement > 0) return "Card's balance is too low. Please recharge: " + requirement;
		else return null;
	}

	/**
	 * Checks for enough money and return corresponding error message if there's any error..
	 *
	 * @param certificateId the certificate id
	 * @param stationID the station ID
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private String hasEnoughMoney(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		PrepaidCard card = getCardById(certificateId);
		int startingStationID = card.getCurrentTrip().getEmbarkingStationID();
		StationHelper helper = new StationHelper();
		double requirement = helper.calculateFare(startingStationID, stationID) -  card.getBalance();
		if(requirement > 0) return "Card's balance is too low. Please recharge: " + requirement;
		else return null;
	}
}
