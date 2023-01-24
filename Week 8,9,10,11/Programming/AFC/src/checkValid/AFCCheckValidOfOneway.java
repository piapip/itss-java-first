package checkValid;


import certificate.Config;
import certificate.OnewayTicket;
import certificate.OnewayTicketHelper;

import java.sql.SQLException;

import station.*;

public class AFCCheckValidOfOneway extends OnewayTicketHelper implements AFCCheckValidOfCertificate {

	
	public AFCCheckValidOfOneway() throws SQLException, ClassNotFoundException {
		super();
	}

	@Override
	public String isValidEnter(String certificateId, int stationID)	throws ClassNotFoundException, SQLException {
		String warning = isExist(certificateId);
		if(warning != null) return warning;
		
		warning = isNew(certificateId);
		if (warning != null) return warning;
		
		warning = entranceStationBetweenTwoD_station(certificateId,stationID);
		if (warning != null) return warning;
		else return null;
	}

	@Override
	public String isValidExit(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		String warning = isExist(certificateId);
		if(warning != null) return warning;
		
		warning = exceedDefaultFare(certificateId, stationID);
		if (warning != null) return warning;
		else return null;
	}

	private String isExist(String certificateId) throws ClassNotFoundException, SQLException {
		if (getOnewayById(certificateId) == null) return "Ticket doesn't exist. Please buy a new one.";
		else return null;
	}
	
	private String isNew(String certificateId) throws ClassNotFoundException, SQLException {
		if (getOnewayById(certificateId).getStatus() != Config.NEW) return "Ticket has been used. Please buy a new one.";
		else return null;
	}
	
	// For enter
	private String entranceStationBetweenTwoD_station(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		OnewayTicket oneway = getOnewayById(certificateId);
		int D_end_StationID = oneway.getD_start_station_ID();
		int D_start_StationID = oneway.getD_end_station_ID();
		StationHelper helper = new StationHelper();
		int A_startStationID=helper.getStationById(stationID).getId();

		if (!((D_start_StationID < A_startStationID && A_startStationID < D_end_StationID) || (D_start_StationID > A_startStationID && A_startStationID > D_end_StationID)))
			return "Invalid entrance station. Please enter a station between. " + helper.getStationById(oneway.getD_start_station_ID()).getName() + " and " + helper.getStationById(oneway.getD_end_station_ID()).getName();
		else return null;
	}
	
	// For exit
	private String exceedDefaultFare(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		OnewayTicket oneway = getOnewayById(certificateId);
		int startingStationID = oneway.getCurrentTrip().getEmbarkingStationID();
		StationHelper helper = new StationHelper();
		double requirement = helper.calculateFare(startingStationID, stationID);
		//TODO: Tell customer which stations is valid, can disembark
		//TODO: If startStation=endStation, handle how? 
		if(requirement > oneway.getD_fare()) return "Actual travel fare ("+ requirement + ") exceeds the default fare ("+ oneway.getD_fare() +") in the ticket.";
		else return null;
	}
}
