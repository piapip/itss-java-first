package station;

import java.sql.SQLException;
import certificate.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class StationHelper.
 *
 * @author Pham Huu Tho
 * 
 * This class will give you the information of which station follows this class's rule and the respective distance and fee counted by that rule.
 */
public class StationHelper extends StationSystem implements Helper{
	
	/**
	 * Instantiates a new station helper.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public StationHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	/**
	 * Gets the station by id.
	 *
	 * @param id the id
	 * @return the station by id
	 */
	public Station getStationById (int id) {
		return stations.get(id-1);
	}
	
	/**
	 * Calculate distance between the two stations with the corresponding ids.
	 *
	 * @param startId the start id
	 * @param destinationId the destination id
	 * @return the double
	 */
	private double calculateDistance(int startId, int destinationId) {
		return Math.abs(this.getStationById(startId).getDistanceToTerminus() - this.getStationById(destinationId).getDistanceToTerminus());
	}
	
	/**
	 * Calculate fare between the two stations with the corresponding ids.
	 *
	 * @param startId the start id
	 * @param destinationId the destination id
	 * @return the double
	 */
	public double calculateFare(int startId, int destinationId) {
		double distance = calculateDistance(startId, destinationId);
		double cost = 0;
		if (distance <= Config.BASED_DISTANCE) cost = Config.BASED_FARE;
		else {
			distance = distance - Config.BASED_DISTANCE;
			cost = Config.BASED_FARE + Config.ADDITIONAL_FARE*Math.ceil(distance/Config.DEFAULT_ADDITION_DISTANCE);		
		}
		return cost;
	}
	
	/**
	 * Gets the lowest cost counted from the station with the corresponding id.
	 *
	 * @param stationId the station id
	 * @return the lowest cost
	 */
	public double getLowestCost(int stationId) {
		if(stationId == 1) {
			return calculateFare(1, 2);
		} else if (stationId == stations.size()) {
			return calculateFare(stations.size()-1, stations.size());
		} else {
			double downStreamCost = calculateFare(stationId-1, stationId);
			double upStreamCost = calculateFare(stationId+1, stationId);
			return Math.min(downStreamCost, upStreamCost);
		}
	}
}
