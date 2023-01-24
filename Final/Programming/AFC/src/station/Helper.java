package station;

// TODO: Auto-generated Javadoc
/**
 * The Interface Helper.
 *
 * @author Pham Huu Tho
 * This interface will be applied to any class that apply new rules of calculating distance and fare between stations.
 */
public interface Helper {
	
	/**
	 * Calculate fare.
	 *
	 * @param startId the start id
	 * @param destinationId the destination id
	 * @return the double
	 */
	public double calculateFare(int startId, int destinationId);
	
	/**
	 * Gets the lowest cost.
	 *
	 * @param stationId the station id
	 * @return the lowest cost
	 */
	public double getLowestCost(int stationId);
}
