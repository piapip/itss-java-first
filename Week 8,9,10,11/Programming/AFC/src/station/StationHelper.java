package station;

import java.sql.SQLException;
import certificate.Config;

/**
 * @author thovi
 * This class will give you the information of which station follows this class's rule and the respective distance and fee counted by that rule.
 */
public class StationHelper extends StationSystem implements Helper{
	
	public StationHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	public Station getStationById (int id) {
		return stations.get(id-1);
	}
	
	private double calculateDistance(int startId, int destinationId) {
		return Math.abs(this.getStationById(startId).getDistanceToTerminus() - this.getStationById(destinationId).getDistanceToTerminus());
	}
	
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
