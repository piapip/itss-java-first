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
 * @author phung-trang
 * A class that describes a 24 hour ticket's information, how it setTime, what happened after it updates its information.
 */
public class Hour24Ticket extends TravelCertificateInformation {
	private int status;
	private String activeTime;
	private String expiredTime;
	

	public Hour24Ticket(String hour24Id, int status, String activeTime, String expiredTime ) {
		super(hour24Id, Config.HOUR24_TYPE);
		this.status = status;
		this.activeTime=activeTime;
		this.expiredTime=expiredTime;
		
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime dateActive = LocalDateTime.now();
		this.activeTime = dtf.format(dateActive);
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime() {
		Calendar expTime = Calendar.getInstance();
		SimpleDateFormat dtf  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		expTime.add(Calendar.DAY_OF_MONTH, 1);
		Date dateExpired = (Date) expTime.getTime();
		this.expiredTime = dtf.format(  dateExpired.getTime());
	}
	protected void markEmbarkingInformation(int stationID) throws ClassNotFoundException, SQLException {
		TransactionHistory currentTrip = this.getCurrentTrip();
		if(currentTrip.getStatus() != Config.UNUSED) 
			return;
		currentTrip.setEmbarkingStationID(stationID);
		TransactionHelper helper = new TransactionHelper();
		int trip_id = currentTrip.getId();
		helper.updateTransaction(trip_id, currentTrip);
	}
	
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