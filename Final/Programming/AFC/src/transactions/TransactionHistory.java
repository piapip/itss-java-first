package transactions;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import certificate.Config;
import station.Helper;
import station.StationHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionHistory.
 *
 * @author Pham Huu Tho
 * A class to describe a transaction's information and what action it can do.
 */
public class TransactionHistory {
	
	/** The id. */
	private int id;
	
	/** The certificate id. */
	private String certificateId;
	
	/** The status. */
	private int status;
	
	/** The day in. */
	private String dayIn;
	
	/** The day out. */
	private String dayOut;
	
	/** The embarking station ID. */
	private int embarkingStationID;
	
	/** The ending station ID. */
	private int endingStationID;
	
	/**
	 * Instantiates a new transaction history.
	 *
	 * @param id the id
	 * @param certificateId the certificate id
	 * @param status the status
	 * @param dayIn the day in
	 * @param dayOut the day out
	 * @param embarkingStationID the embarking station ID
	 * @param endingStationID the ending station ID
	 */
	public TransactionHistory(int id, String certificateId, int status, String dayIn, String dayOut, int embarkingStationID, int endingStationID) {
		this.id = id;
		this.certificateId = certificateId;
		this.status = status;
		this.dayIn = dayIn;
		this.dayOut = dayOut;
		this.embarkingStationID = embarkingStationID;
		this.endingStationID = endingStationID;
	}

	/**
	 * Gets the certificate id.
	 *
	 * @return the certificate id
	 */
	protected String getCertificateId() {
		return certificateId;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
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
	 * Gets the day in.
	 *
	 * @return the day in
	 */
	public String getDayIn() {
		return dayIn;
	}
	
	/**
	 * Gets the day out.
	 *
	 * @return the day out
	 */
	public String getDayOut() {
		return dayOut;
	}
	
	/**
	 * Gets the embarking station ID.
	 *
	 * @return the embarking station ID
	 */
	public int getEmbarkingStationID() {
		return embarkingStationID;
	}
	
	/**
	 * Gets the ending station ID.
	 *
	 * @return the ending station ID
	 */
	public int getEndingStationID() {
		return endingStationID;
	}
	
	/**
	 * Sets the embarking station ID.
	 *
	 * @param embarkingStationID the new embarking station ID
	 */
	public void setEmbarkingStationID(int embarkingStationID) {
		if(this.status == Config.UNUSED) {
			this.embarkingStationID = embarkingStationID;
			this.status = Config.PENDING;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			this.dayIn = dtf.format(now);
		}
	}
	
	/**
	 * Sets the ending station ID.
	 *
	 * @param endingStationID the new ending station ID
	 */
	public void setEndingStationID(int endingStationID) {
		if(this.status == Config.PENDING) {
			this.endingStationID = endingStationID;
			this.status = Config.SUCCESSFUL;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			this.dayOut = dtf.format(now);
		}
	}	
	
	/**
	 * Gets the fee of the transaction.
	 *
	 * @return the fee
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public double getFee() throws ClassNotFoundException, SQLException {
		double price = 0;
		Helper helper = new StationHelper();		
		if(this.status == Config.SUCCESSFUL) {
			price = helper.calculateFare(this.embarkingStationID, this.endingStationID);
		}
		return price;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
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
