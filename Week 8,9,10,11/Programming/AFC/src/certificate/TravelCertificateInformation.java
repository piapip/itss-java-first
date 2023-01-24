package certificate;

import java.sql.SQLException;
import java.util.List;

import transactions.TransactionHelper;
import transactions.TransactionHistory;

/**
 * @author thovi
 * A common class for every certificates to inherit. Has common actions that all classes of certificate can do.
 */
public class TravelCertificateInformation {
	private String id;
	private int type;
	
	public TravelCertificateInformation(String id) {
		this.id = id;
	}
	
	public TravelCertificateInformation(String id, int type) {
		this.id = id;
		this.type = type;		
	}	
	
	public String getID() {
		return this.id;
	}
	
	public int getType() {
		return this.type;
	}
	
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
	
	protected void markEmbarkingInformation(int stationID) throws ClassNotFoundException, SQLException {
		TransactionHistory currentTrip = this.getCurrentTrip();
		if(currentTrip.getStatus() != Config.UNUSED) 
			return;
		currentTrip.setEmbarkingStationID(stationID);
		TransactionHelper helper = new TransactionHelper();
		int trip_id = currentTrip.getId();
		helper.updateTransaction(trip_id, currentTrip);
	}
	
	protected void markEndingInformation(int stationID) throws ClassNotFoundException, SQLException {
		TransactionHistory currentTrip = this.getCurrentTrip();
		if(currentTrip.getStatus() != Config.PENDING || currentTrip.getEmbarkingStationID() == stationID)
			return;
		currentTrip.setEndingStationID(stationID);
		TransactionHelper helper = new TransactionHelper();
		helper.updateTransaction(currentTrip.getId(), currentTrip);
	}
	
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
