package certificate;

import java.sql.SQLException;
import java.util.List;

import transactions.TransactionHelper;
import transactions.TransactionHistory;

// TODO: Auto-generated Javadoc
/**
 * The Class TravelCertificateInformation.
 *
 * @author Pham Huu Tho
 * A common class for every certificates to inherit. Has common actions that all classes of certificate can do.
 */
public class TravelCertificateInformation {
	
	/** The id. */
	private String id;
	
	/** The type. */
	private int type;
	
	/**
	 * Instantiates a new travel certificate information.
	 *
	 * @param id the id
	 */
	public TravelCertificateInformation(String id) {
		this.id = id;
	}
	
	/**
	 * Instantiates a new travel certificate information.
	 *
	 * @param id the id
	 * @param type the type
	 */
	public TravelCertificateInformation(String id, int type) {
		this.id = id;
		this.type = type;		
	}	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType() {
		return this.type;
	}
	
	/**
	 * Gets the current/latest trip of the card.
	 *
	 * @return the current trip
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public TransactionHistory getCurrentTrip() throws ClassNotFoundException, SQLException {
		TransactionHelper helper = new TransactionHelper();
		List<TransactionHistory> transactionList = helper.getTransactionByCertificateId(this.id);
		if(transactionList.size() != 0) {
			return transactionList.get(transactionList.size()-1);
		} else {
			helper.addTransaction(this.id);
			return getCurrentTrip();
		}
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
	protected void markEndingInformation(int stationID) throws ClassNotFoundException, SQLException {
		TransactionHistory currentTrip = this.getCurrentTrip();
		if(currentTrip.getStatus() != Config.PENDING)
			return;
		currentTrip.setEndingStationID(stationID);
		TransactionHelper helper = new TransactionHelper();
		helper.updateTransaction(currentTrip.getId(), currentTrip);
	}
	
	/**
	 * Generic information of the certificate.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		String typeName;
		if(this.type == 1) typeName = "Oneway ticket";
		else if(this.type == 2) typeName = "24-hour ticket";
		else if(this.type == 3) typeName = "Prepaid card";
		else typeName = null;
		return String.format("ID: " + this.id + "\nType: " + typeName);
	}
}
