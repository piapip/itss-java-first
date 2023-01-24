package transactions;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import certificate.Config;
import station.Helper;
import station.StationHelper;

/**
 * @author thovi
 * A class to describe a transaction's information and what action it can do.
 */
public class TransactionHistory {
	
	private int id;
	private String certificateId;
	private int status;
	private String dayIn;
	private String dayOut;
	private int embarkingStationID;
	private int endingStationID;
	
	public TransactionHistory(int id, String certificateId, int status, String dayIn, String dayOut, int embarkingStationID, int endingStationID) {
		this.id = id;
		this.certificateId = certificateId;
		this.status = status;
		this.dayIn = dayIn;
		this.dayOut = dayOut;
		this.embarkingStationID = embarkingStationID;
		this.endingStationID = endingStationID;
	}

	protected String getCertificateId() {
		return certificateId;
	}

	public int getId() {
		return id;
	}
	public int getStatus() {
		return status;
	}	
	public String getDayIn() {
		return dayIn;
	}
	public String getDayOut() {
		return dayOut;
	}
	public int getEmbarkingStationID() {
		return embarkingStationID;
	}
	public int getEndingStationID() {
		return endingStationID;
	}
	
	public void setEmbarkingStationID(int embarkingStationID) {
		if(this.status == Config.UNUSED) {
			this.embarkingStationID = embarkingStationID;
			this.status = Config.PENDING;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			this.dayIn = dtf.format(now);
		}
	}
	
	public void setEndingStationID(int endingStationID) {
		if(this.status == Config.PENDING) {
			this.endingStationID = endingStationID;
			this.status = Config.SUCCESSFUL;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			this.dayOut = dtf.format(now);
		}
	}	
	
	public double getFee() throws ClassNotFoundException, SQLException {
		double price = 0;
		Helper helper = new StationHelper();		
		if(this.status == Config.SUCCESSFUL) {
			price = helper.calculateFare(this.embarkingStationID, this.endingStationID);
		}
		return price;
	}
	
	@Override
	public String toString() {
		StationHelper helper;
		try {
			helper = new StationHelper();
			String result = null;
			if(this.embarkingStationID != 0) {
				result = result + "Embarking station: " + helper.getStationById(embarkingStationID).getName() + "\n";
				result = result + "Day in: " + this.dayIn + "\n";
			}
			if(this.endingStationID != 0) {
				result = result + "Ending station: " + helper.getStationById(endingStationID).getName() + "\n";
				result = result + "Day out: " + this.dayIn + "\n";
			}
			return String.format(result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
