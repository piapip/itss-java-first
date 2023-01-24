package certificate;

import java.sql.SQLException;

import transactions.TransactionHelper;
import transactions.TransactionHistory;

/**
 * The Class PrepaidCard.
 * 
 * @author Pham Huu Tho
 */
public class PrepaidCard extends TravelCertificateInformation{
	
	/** The balance. */
	private double balance;
	
	/**
	 * Instantiates a new prepaid card.
	 *
	 * @param cardId the card id
	 * @param balance the balance
	 */
	public PrepaidCard(String cardId, double balance) {
		super(cardId, Config.PREPAID_TYPE);
		this.balance = balance;
	}
	
	/**
	 * Gets the balance.
	 *
	 * @return the balance
	 */
	public double getBalance() {
		return this.balance;
	}
	
	/**
	 * Action "Pay" of Prepaid card .
	 *
	 * @return true, if successful
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
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
	
	/**
	 * Action "Mark embarking information" of PrepaidCard.
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
	 * Action "Mark ending information" of PrepaidCard.
	 *
	 * @param stationID the station ID
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	protected void markEndingInformation(int stationID) throws ClassNotFoundException, SQLException {
		super.markEndingInformation(stationID);
		TransactionHelper helper = new TransactionHelper();
		this.pay();
		helper.addTransaction(this.getID());
	}
	
}
