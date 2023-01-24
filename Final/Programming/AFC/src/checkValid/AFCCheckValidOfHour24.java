package checkValid;

import java.sql.SQLException;

import certificate.Config;
import certificate.Hour24Ticket;
import certificate.Hour24TicketHeper;

/**
 * The Class AFCCheckValidOfHour24.
 *
 * @author phung-trang
 * Return warning messages if Hour24Ticket has any problem with passing the entry.
 */
public class AFCCheckValidOfHour24 extends Hour24TicketHeper implements AFCCheckValidOfCertificate{
	
	/**
	 * Instantiates a new AFC check valid of hour 24.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public AFCCheckValidOfHour24() throws SQLException, ClassNotFoundException {
		
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
	public String isValidEnter(String certificateId, int stationID )
			throws ClassNotFoundException, SQLException {
		
		String warning = isExist(certificateId);
		if(warning != null) return warning;
		
		warning = markTime(certificateId);
		if(warning != null) return warning;
		
		warning = checkTime(certificateId);
		if(warning != null) return warning;
		
		else return null;
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
	public String isValidExit(String certificateId, int stationID)
			throws ClassNotFoundException, SQLException {
		
		String warning = isExist(certificateId);
		if(warning != null) return warning;
		
		warning = markTime(certificateId);
		if(warning != null) return warning;
		
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
		if (getHour24ById(certificateId) == null) return "Ticket doesn't exist. Please buy a new one.";
		else return null;
	}
	
	/**
	 * Mark time.
	 *
	 * @param certificateId the id of traveling certificate
	 * @return the warning messages if has any problem
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private String markTime(String certificateId)throws ClassNotFoundException, SQLException {
		Hour24TicketHeper helper = new Hour24TicketHeper();
		Hour24Ticket hour24 = helper.getHour24ById(certificateId);
		hour24.setActiveTime();
		if(hour24.getStatus() == Config.NEW) {
			hour24.setStatus(Config.PENDING);
			hour24.setExpiredTime();
			helper.updateHour24(certificateId, hour24);
			return null;
		}else if(hour24.getStatus() == Config.PENDING){
			helper.updateHour24(certificateId, hour24);
			return null;
		}else return "Invalid ticket 24 hour\n ID:" +hour24.getID() + "  valid until " + hour24.getExpiredTime() + "\nExpired: Try to enter at  " + hour24.getActiveTime();
		
	}
	
	/**
	 * Check time.
	 *
	 * @param certificateId the id of traveling certificate
	 * @return the warning messages if has any problem
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private String checkTime(String certificateId) throws ClassNotFoundException, SQLException{
		Hour24TicketHeper helper = new Hour24TicketHeper();
		Hour24Ticket hour24 = helper.getHour24ById(certificateId);
		if((hour24.getActiveTime()).compareTo(hour24.getExpiredTime())==-1) return null;
		else {
			hour24.setStatus(Config.DESTROYED);
			helper.updateHour24(certificateId, hour24);
			return "Invalid ticket 24 hour\n ID:" +hour24.getID() + "  valid until " + hour24.getExpiredTime() + "\nExpired: Try to enter at  " + hour24.getActiveTime();
		}
	}
}
