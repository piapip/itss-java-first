package certificate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import transactions.TransactionHelper;
import transactions.TransactionHistory;

/**
 * The Class Hour24Ticket.
 *
 * @author phung-trang
 * A class that describes a 24 hour ticket's information, how it setTime, what happened after it updates its information.
 */
public class Hour24Ticket extends TravelCertificateInformation {
	
	/** The status of hour 24 ticket. */
	private int status;
	
	/** The active time of hour 24 ticket. */
	private String activeTime;
	
	/** The expired time of hour 24 ticket. */
	private String expiredTime;
	

	/**
	 * Instantiates a new hour 24 ticket.
	 *
	 * @param hour24Id the id of hour 24 ticket
	 * @param status the status of hour 24 ticket
	 * @param activeTime the active time of hour 24 ticket
	 * @param expiredTime the expired time of hour 24 ticket
	 */
	public Hour24Ticket(String hour24Id, int status, String activeTime, String expiredTime ) {
		super(hour24Id, Config.HOUR24_TYPE);
		this.status = status;
		this.activeTime=activeTime;
		this.expiredTime=expiredTime;
		
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Gets the active time.
	 *
	 * @return the active time
	 */
	public String getActiveTime() {
		return activeTime;
	}

	/**
	 * Sets the active time.
	 */
	public void setActiveTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateActive = LocalDateTime.now();
		this.activeTime = dtf.format(dateActive);
	}

	/**
	 * Gets the expired time.
	 *
	 * @return expiredTime 
	 */
	public String getExpiredTime() {
		return expiredTime;
	}

	/**
	 * Sets the expired time of 24 hour ticket.
	 */
	public void setExpiredTime() {
		Calendar expTime = Calendar.getInstance();
		SimpleDateFormat dtf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		expTime.add(Calendar.DAY_OF_MONTH, 1);
		Date dateExpired = (Date) expTime.getTime();
		this.expiredTime = dtf.format(  dateExpired.getTime());
	}
	
	/**
	 * Mark embarking information.
	 *
	 * @param stationID the station ID
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	protected void markEmbarkingInformation(int stationID) throws ClassNotFoundException, SQLException {
		TransactionHistory currentTrip = this.getCurrentTrip();
		if(currentTrip.getStatus() != Config.UNUSED) 
			return;
		currentTrip.setEmbarkingStationID(stationID);
		TransactionHelper helper = new TransactionHelper();
		int trip_id = currentTrip.getId();
		helper.updateTransaction(trip_id, currentTrip);
	}
	
	/**
	 * Mark ending information.
	 *
	 * @param stationID the station ID
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public void markEndingInformation(int stationID) throws ClassNotFoundException, SQLException {
		TransactionHistory currentTrip = this.getCurrentTrip();
		if(currentTrip.getStatus() != Config.PENDING || currentTrip.getEmbarkingStationID() == stationID)
			return;
		currentTrip.setEndingStationID(stationID);
		TransactionHelper helper = new TransactionHelper();
		helper.updateTransaction(currentTrip.getId(), currentTrip);
		helper.addTransaction(this.getID());
	}
	
}