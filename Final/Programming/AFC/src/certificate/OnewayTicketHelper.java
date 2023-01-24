package certificate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import db.ConnectToMySQL;

/**
 * The Class OnewayTicketHelper.
 * 
 * @author NguyenManhTien
 * Create connections to the database and perform UPDATE(with preconditions) and READ actions.
 */
public class OnewayTicketHelper implements CertificateHelper {

	/**
	 * Connect to the data base of one way ticket.
	 *
	 * @return the connection to DB of one way ticket
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private Connection ConnectToOneWayDB() throws SQLException, ClassNotFoundException {
		return ConnectToMySQL.getInformation("travelling_certificate");
	}

	/**
	 * Gets the one way ticket by id.
	 *
	 * @param ID the id of traveling certificate
	 * @return the one way ticket from this id
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public OnewayTicket getOnewayById(String ID) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToOneWayDB();
		Statement statement = connection.createStatement();
		String sql = "Select * from oneway_ticket WHERE id='" + ID + "'";
		ResultSet rs = statement.executeQuery(sql);
		if (rs != null) {
			while (rs.next()) {
				int status = rs.getInt(2);
				int d_start_station_ID = rs.getInt(3);
				int d_end_station_ID = rs.getInt(4);
				double d_fare = rs.getDouble(5);
				connection.close();
				return new OnewayTicket(ID, status, d_start_station_ID, d_end_station_ID, d_fare);
			}
		}
		connection.close();
		return null;
	}

	/**
	 * Update status one way ticket.
	 *
	 * @param ID the id of one way ticket which wants to update
	 * @param newOneway the new one way ticket with new status
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void updateStatusOneway(String ID, OnewayTicket newOneway) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectToMySQL.getInformation("travelling_certificate");
		Statement statement = connection.createStatement();
		String sql = "UPDATE oneway_ticket SET status=\"" + newOneway.getStatus() + "\" WHERE id=\"" + newOneway.getID()
				+ "\"";
		statement.executeUpdate(sql);
		connection.close();
	}

	/**
	 * Mark station.
	 *
	 * @param ID the id of the one way ticket
	 * @param stationID the station ID which want to mark
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public void markStation(String ID, int stationID) throws ClassNotFoundException, SQLException {
		OnewayTicket oneway = this.getOnewayById(ID);
		int tripStatus = oneway.getCurrentTrip().getStatus();
		if (tripStatus == Config.UNUSED) {
			oneway.markEmbarkingInformation(stationID);
			this.updateStatusOneway(ID, oneway);
		} else if (tripStatus == Config.PENDING) {
			oneway.markEndingInformation(stationID);
			this.updateStatusOneway(ID, oneway);
		}
	}
}
