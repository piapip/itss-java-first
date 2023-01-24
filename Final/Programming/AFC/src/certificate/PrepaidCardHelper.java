package certificate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectToMySQL;

/**
 * The Class PrepaidCardHelper.
 *
 * @author Pham Huu Tho
 * Create connections to the database and perform UPDATE(with preconditions) and READ actions.
 */
public class PrepaidCardHelper implements CertificateHelper {
	
	/**
	 * Create a connection to prepaid card database.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private Connection ConnectToPrepaidCardDB() throws SQLException, ClassNotFoundException {
		return ConnectToMySQL.getInformation("travelling_certificate");
	}
	
	/**
	 * Gets the card by id.
	 *
	 * @param ID the id
	 * @return the card by id
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public PrepaidCard getCardById(String ID) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToPrepaidCardDB();
		Statement statement = connection.createStatement();
		String sql = "Select * from prepaid_card WHERE id='" + ID + "'";
		ResultSet rs = statement.executeQuery(sql);
		if(rs != null) {
			while(rs.next()){
				double balance = rs.getDouble(2);
				connection.close();
				return new PrepaidCard(ID, balance);
			}
		}
		connection.close();
		return null;
	}
	
	/**
	 * Mark station.
	 *
	 * @param ID the id
	 * @param stationID the station ID
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public void markStation(String ID, int stationID) throws ClassNotFoundException, SQLException {
		PrepaidCard card = this.getCardById(ID);
		int tripStatus = card.getCurrentTrip().getStatus();
		if(tripStatus == Config.UNUSED) {
			card.markEmbarkingInformation(stationID);
		} else if (tripStatus == Config.PENDING) {
			card.markEndingInformation(stationID);
			this.updateCard(ID, card);
		}
	}
	
	/**
	 * Update card.
	 *
	 * @param ID the id
	 * @param newCard the new card
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void updateCard(String ID, PrepaidCard newCard) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectToMySQL.getInformation("travelling_certificate");
		Statement statement = connection.createStatement();
		String sql = "UPDATE prepaid_card SET balance=\"" + newCard.getBalance()+"\" WHERE id=\"" + newCard.getID()+ "\"";
		statement.executeUpdate(sql);
		connection.close();
	}
}
