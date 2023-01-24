package station;

/**
 * @author thovi
 * This interface will be applied to any class that apply new rules of calculating distance and fare between stations.
 */
public interface Helper {
	public double calculateFare(int startId, int destinationId);
	public double getLowestCost(int stationId);
}
