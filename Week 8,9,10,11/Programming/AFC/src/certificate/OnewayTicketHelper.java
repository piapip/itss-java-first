package certificate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import db.ConnectToMySQL;

public class OnewayTicketHelper implements CertificateHelper {
	
	private Connection ConnectToOneWayDB() throws SQLException, ClassNotFoundException {
		return ConnectToMySQL.getInformation("travelling_certificate");
	}

	public OnewayTicket getOnewayById(String ID) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToOneWayDB();
		Statement statement = connection.createStatement();
		String sql = "Select * from oneway_ticket WHERE id='" + ID + "'";
		ResultSet rs = statement.executeQuery(sql);
		if(rs != null) {
			while(rs.next()){
				int status = rs.getInt(2);
				int d_start_station_ID = rs.getInt(3);
				int d_end_station_ID = rs.getInt(4);
				double d_fare = rs.getDouble(5);
				connection.close();
				return new OnewayTicket(ID,status,d_start_station_ID,d_end_station_ID,d_fare);
			}
		}
		connection.close();
		return null;
	}

	public void updateStatusOneway(String ID, OnewayTicket newOneway) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectToMySQL.getInformation("travelling_certificate");
		Statement statement = connection.createStatement();
		String sql = "UPDATE onewayticket SET status=\"" + newOneway.getStatus() + "\" WHERE id=\"" + newOneway.getID()
				+ "\"";
		statement.executeUpdate(sql);
		connection.close();
	}
	
	public void markStation(String ID, int stationID) throws ClassNotFoundException, SQLException {
		OnewayTicket oneway = this.getOnewayById(ID);
		int tripStatus = oneway.getCurrentTrip().getStatus();
		if(tripStatus == Config.UNUSED) {
			oneway.markEmbarkingInformation(stationID);
		} else if (tripStatus == Config.PENDING) {
			oneway.markEndingInformation(stationID);	
			this.updateStatusOneway(ID, oneway);
		}
	}
}
