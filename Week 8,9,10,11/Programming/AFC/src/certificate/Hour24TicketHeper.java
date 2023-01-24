package certificate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import db.ConnectToMySQL;
/**
 * @author phung-trang
 * Create connections to the database and perform UPDATE(with preconditions) and READ actions.
 */
public class Hour24TicketHeper implements CertificateHelper {
	
	private Connection ConnectToHour24TicketDB() throws SQLException, ClassNotFoundException {
		return ConnectToMySQL.getInformation("travelling_certificate");
	}
	
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