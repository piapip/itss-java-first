package checkValid;

import certificate.PrepaidCard;
import certificate.PrepaidCardHelper;

import java.sql.SQLException;

import station.*;

/**
 * @author thovi
 * Return warning messages if PrepaidCard has any problem with passing the entry.
 */
public class AFCCheckValidOfPrepaidCard extends PrepaidCardHelper implements AFCCheckValidOfCertificate{

	public AFCCheckValidOfPrepaidCard() throws SQLException, ClassNotFoundException {
		super();
	}

	@Override
	public String isValidEnter(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		String warning = isExist(certificateId);
		if(warning != null) return warning;
		
		warning = exceedBaseFare(certificateId, stationID);
		if(warning != null) return warning;
		
		else return null;
	}
	
	@Override
	public String isValidExit(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		String warning = isExist(certificateId);
		if(warning != null) return warning; 
		
		warning = hasEnoughMoney(certificateId, stationID);
		if(warning != null) return warning;
		else return null;
	}
	
	private String isExist(String certificateId) throws ClassNotFoundException, SQLException {
		if (getCardById(certificateId) == null) return "Card doesn't exist. Please buy a new one.";
		return null;
	}
	
	private String exceedBaseFare(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		StationHelper helper = new StationHelper();
		double requirement = helper.getLowestCost(stationID) - getCardById(certificateId).getBalance();
		if(requirement > 0) return "Card's balance is too low. Please recharge: " + requirement;
		else return null;
	}

	private String hasEnoughMoney(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		PrepaidCard card = getCardById(certificateId);
		int startingStationID = card.getCurrentTrip().getEmbarkingStationID();
		StationHelper helper = new StationHelper();
		double requirement = helper.calculateFare(startingStationID, stationID) -  card.getBalance();
		if(requirement > 0) return "Card's balance is too low. Please recharge: " + requirement;
		else return null;
	}
}
