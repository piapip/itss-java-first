package checkValid;

import certificate.Config;
import certificate.OnewayTicket;
import certificate.OnewayTicketHelper;

import java.sql.SQLException;

import station.*;

/**
 * The Class AFCCheckValidOfOneway.
 * 
 * @author NguyenManhTien
 * Return warning messages if one way ticket has any problem with passing the entry.
 */
public class AFCCheckValidOfOneway extends OnewayTicketHelper implements AFCCheckValidOfCertificate {

	/**
	 * Instantiates a new AFC check valid of one way ticket.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public AFCCheckValidOfOneway() throws SQLException, ClassNotFoundException {
		super();
	}

	/**
	 * Checks if is valid enter.
	 *
	 * @param certificateId the id of traveling certificate
	 * @param stationID the ID of actual entering station
	 * @return the warning messages if has any problem
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@Override
	public String isValidEnter(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		String warning = isExist(certificateId);
		if (warning != null)
			return warning;

		warning = isNew(certificateId);
		if (warning != null)
			return warning;

		warning = entranceStationBetweenTwoD_station(certificateId, stationID);
		if (warning != null)
			return warning;
		else
			return null;
	}

	/**
	 * Checks if is valid exit.
	 *
	 * @param certificateId the id of traveling certificate
	 * @param stationID the ID of actual exiting station
	 * @return the warning messages if has any problem
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@Override
	public String isValidExit(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		String warning = isExist(certificateId);
		if (warning != null)
			return warning;

		warning = isNew(certificateId);
		if (warning != null)
			return warning;

		warning = exceedDefaultFare(certificateId, stationID);
		if (warning != null)
			return warning;
		else
			return null;
	}

	/**
	 * Checks if is exist.
	 *
	 * @param certificateId the id of traveling certificate
	 * @return the warning messages if has any problem
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private String isExist(String certificateId) throws ClassNotFoundException, SQLException {
		if (getOnewayById(certificateId) == null)
			return "Ticket doesn't exist. Please buy a new one.";
		else
			return null;
	}

	/**
	 * Checks if is new (unused).
	 *
	 * @param certificateId the id of traveling certificate
	 * @return the warning messages if has any problem
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private String isNew(String certificateId) throws ClassNotFoundException, SQLException {
		if (getOnewayById(certificateId).getStatus() == Config.DESTROYED)
			return "Ticket has been used. Please buy a new one.";
		else
			return null;
	}

	/**
	 * Check if entering station is between two default station of one way ticket.
	 *
	 * @param certificateId the id of traveling certificate
	 * @param stationID the ID of actual entering station
	 * @return the warning messages if has any problem
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	// For enter
	private String entranceStationBetweenTwoD_station(String certificateId, int stationID)
			throws ClassNotFoundException, SQLException {
		OnewayTicket oneway = getOnewayById(certificateId);
		int D_end_StationID = oneway.getD_start_station_ID();
		int D_start_StationID = oneway.getD_end_station_ID();
		StationHelper helper = new StationHelper();
		int A_startStationID = helper.getStationById(stationID).getId();

		if (!((D_start_StationID <= A_startStationID && A_startStationID <= D_end_StationID)
				|| (D_start_StationID >= A_startStationID && A_startStationID >= D_end_StationID)))
			return "Invalid entrance station. Please enter a station between. "
					+ helper.getStationById(oneway.getD_start_station_ID()).getName() + " and "
					+ helper.getStationById(oneway.getD_end_station_ID()).getName();
		else
			return null;
	}

	/**
	 * Check if the exiting station which has fare exceed default fare of one way ticket.
	 *
	 * @param certificateId the id of traveling certificate
	 * @param stationID the ID of actual exiting station
	 * @return the warning messages if has any problem
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	// For exit
	private String exceedDefaultFare(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		OnewayTicket oneway = getOnewayById(certificateId);
		int startingStationID = oneway.getCurrentTrip().getEmbarkingStationID();
		StationHelper helper = new StationHelper();
		double requirement = helper.calculateFare(startingStationID, stationID);
		if (requirement > oneway.getD_fare())
			return "Actual travel fare (" + requirement + ") exceeds the default fare (" + oneway.getD_fare()
					+ ") in the ticket.";
		else
			return null;
	}
}
