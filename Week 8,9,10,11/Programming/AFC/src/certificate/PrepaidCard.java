package certificate;

import java.sql.SQLException;

import transactions.TransactionHelper;
import transactions.TransactionHistory;

/**
 * @author thovi
 * A class that describes a prepaidcard's information, how it pays, what happened after it updates its information.
 */
public class PrepaidCard extends TravelCertificateInformation{
	private double balance;
	
	public PrepaidCard(String cardId, double balance) {
		super(cardId, Config.PREPAID_TYPE);
		this.balance = balance;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	protected boolean pay() throws ClassNotFoundException, SQLException {
		TransactionHistory currentTrip = this.getCurrentTrip();
		if(currentTrip.getStatus() == Config.SUCCESSFUL) {
			double price = currentTrip.getFee();
			double afterTransaction = this.balance - price;
			if(afterTransaction < 0) return false;
			else {
				this.balance = afterTransaction;
				return true;
			}	
		}
		return false;
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
		super.markEndingInformation(stationID);
		TransactionHelper helper = new TransactionHelper();
		this.pay();
		helper.addTransaction(this.getID());
	}
	
}
