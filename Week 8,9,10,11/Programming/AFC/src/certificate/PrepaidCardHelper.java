package certificate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectToMySQL;

/**
 * @author thovi
 * Create connections to the database and perform UPDATE(with preconditions) and READ actions.
 */
public class PrepaidCardHelper implements CertificateHelper {
	
	private Connection ConnectToPrepaidCardDB() throws SQLException, ClassNotFoundException {
		return ConnectToMySQL.getInformation("travelling_certificate");
	}
	
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
	
	public void updateCard(String ID, PrepaidCard newCard) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectToMySQL.getInformation("travelling_certificate");
		Statement statement = connection.createStatement();
		String sql = "UPDATE prepaid_card SET balance=\"" + newCard.getBalance()+"\" WHERE id=\"" + newCard.getID()+ "\"";
		statement.executeUpdate(sql);
		connection.close();
	}
}
