package certificate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import db.ConnectToMySQL;

/**
 * The Class Hour24TicketHeper.
 *
 * @author phung-trang
 * Create connections to the database and perform UPDATE(with preconditions) and READ actions.
 */
public class Hour24TicketHeper implements CertificateHelper {
	
	/**
	 * Connect to DB of 24 hour ticket.
	 *
	 * @return the connection to DB of 24 hour ticket
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private Connection ConnectToHour24TicketDB() throws SQLException, ClassNotFoundException {
		return ConnectToMySQL.getInformation("travelling_certificate");
	}
	
	/**
	 * Gets the hour 24 by id.
	 *
	 * @param ID the id of traveling certificate
	 * @return the 24 hour ticket ticket from this id
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public Hour24Ticket getHour24ById(String ID)throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToHour24TicketDB();
		Statement statement = connection.createStatement();
		String sql = "Select * from hour24_ticket WHERE id='" + ID + "'";
		ResultSet rs = statement.executeQuery(sql);
		if(rs != null) {
			while(rs.next()){
				int status = rs.getInt(2);
				String activeTime = rs.getString(3);
				String expiredTime = rs.getString(4);
				connection.close();
				return new Hour24Ticket(ID,status, activeTime, expiredTime);
			}
		}
		connection.close();
		return null;
	}
	
	/**
	 * Update status,activeTime, expiredTime of 24 hour ticket.
	 *
	 * @param ID the id of 24 hour ticket ticket which wants to update
	 * @param newHour24 the new 24 hour ticket with new status
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void updateHour24(String ID, Hour24Ticket newHour24) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectToMySQL.getInformation("travelling_certificate");
		Statement statement = connection.createStatement();
		String sql1 = "UPDATE hour24_ticket SET status=\"" + newHour24.getStatus()+  "\" WHERE id=\"" + newHour24.getID()+ "\"";
		String sql2 = "UPDATE hour24_ticket SET activeTime=\"" + newHour24.getActiveTime()+   "\" WHERE id=\"" + newHour24.getID()+ "\"";
		String sql3 = "UPDATE hour24_ticket SET expiredTime=\"" + newHour24.getExpiredTime()+   "\" WHERE id=\"" + newHour24.getID()+ "\"";
		statement.executeUpdate(sql1);
		statement.executeUpdate(sql2);
		statement.executeUpdate(sql3);
		connection.close();
	}
	
	/**
	 * Mark station.
	 *
	 * @param ID the id of 24 hour ticket
	 * @param stationID the station ID which want to mark
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public void markStation(String ID, int stationID) throws ClassNotFoundException, SQLException {
		Hour24Ticket hour24 = this.getHour24ById(ID);
		int tripStatus = hour24.getCurrentTrip().getStatus();
		if(tripStatus == Config.UNUSED) {
			hour24.markEmbarkingInformation(stationID);
		} else if (tripStatus == Config.PENDING) {
			hour24.markEndingInformation(stationID);	
			this.updateHour24(ID, hour24);
		}
	}
	
}