package certificate;

import java.sql.SQLException;

/**
 * The Class OnewayTicket.
 * 
 * @author NguyenManhTien
 * A class that describes a one way ticket's information.
 */
public class OnewayTicket extends TravelCertificateInformation {
	
	/** The status of one way ticket. */
	private int status;
	
	/** The ID of the default starting station of one way ticket. */
	private int d_start_station_ID;
	
	/** The ID of default ending station of one way ticket. */
	private int d_end_station_ID;
	
	/** The default fare of one way ticket. */
	private double d_fare;

	/**
	 * Instantiates a new one way ticket.
	 *
	 * @param onewayId the id of one way ticket
	 * @param status the status of one way ticket
	 * @param d_start_station_ID the ID of the default starting station of one way ticket
	 * @param d_end_station_ID the ID of the default ending station of one way ticket
	 * @param d_fare the default fare of one way ticket
	 */
	public OnewayTicket(String onewayId, int status, int d_start_station_ID, int d_end_station_ID, double d_fare) {
		super(onewayId, Config.ONEWAY_TYPE);
		this.status = status;
		this.d_start_station_ID = d_start_station_ID;
		this.d_end_station_ID = d_end_station_ID;
		this.d_fare = d_fare;
	}

	/**
	 * Gets the status of the one way ticket.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * Gets the id of the default starting station of one way ticket.
	 *
	 * @return the d_start_station_ID
	 */
	public int getD_start_station_ID() {
		return d_start_station_ID;
	}

	/**
	 * Gets the id of the default ending station of one way ticket.
	 *
	 * @return the d_end_station_ID
	 */
	public int getD_end_station_ID() {
		return d_end_station_ID;
	}

	/**
	 * Gets the default fare of one way ticket.
	 *
	 * @return the d_fare
	 */
	public double getD_fare() {
		return d_fare;
	}

	/**
	 * Mark embarking information.
	 *
	 * @param stationID The ID of actual entering station
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	protected void markEmbarkingInformation(int stationID) throws ClassNotFoundException, SQLException {
		super.markEmbarkingInformation(stationID);
		this.status = Config.IN_STATION;
	}

	/**
	 * Mark ending information.
	 *
	 * @param stationID The ID of actual exiting station
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	protected void markEndingInformation(int stationID) throws ClassNotFoundException, SQLException {
		super.markEndingInformation(stationID);
		this.status = Config.DESTROYED;
	}
}