package certificate;

import java.sql.SQLException;

public class OnewayTicket extends TravelCertificateInformation {
	private int status;
	private int d_start_station_ID;
	private int d_end_station_ID;
	private double d_fare;

	public OnewayTicket(String onewayId, int status, int d_start_station_ID, int d_end_station_ID, double d_fare) {
		super(onewayId, Config.ONEWAY_TYPE);
		this.status = status;
		this.d_start_station_ID = d_start_station_ID;
		this.d_end_station_ID = d_end_station_ID;
		this.d_fare = d_fare;
	}
	public int getStatus() {
		return this.status;
	}
	
	public int getD_start_station_ID() {
		return d_start_station_ID;
	}

	public int getD_end_station_ID() {
		return d_end_station_ID;
	}

	public double getD_fare() {
		return d_fare;
	}
	
	protected void markEmbarkingInformation(int stationID) throws ClassNotFoundException, SQLException {
		super.markEmbarkingInformation(stationID);
		this.status = Config.IN_STATION;
	}
	
	protected void markEndingInformation(int stationID) throws ClassNotFoundException, SQLException {
		super.markEndingInformation(stationID);
		this.status = Config.EXPIRED;
	}
}