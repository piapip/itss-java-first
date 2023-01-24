package station;

// TODO: Auto-generated Javadoc
/**
 * The Class Station.
 *
 * @author Pham Huu Tho
 * A class that describes a station's information.
 */
public class Station {
	
	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
	
	/** The distance to terminus. */
	private double distanceToTerminus;
	
	/**
	 * Instantiates a new station.
	 *
	 * @param id the id
	 * @param name the name
	 * @param distance the distance
	 */
	public Station(int id, String name, double distance) {
		this.id = id;
		this.name = name;
		this.distanceToTerminus = distance;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the distance to terminus.
	 *
	 * @return the distance to terminus
	 */
	public double getDistanceToTerminus() {
		return distanceToTerminus;
	}
	
	/**
	 * Basic information of the station.
	 *
	 * @return the string
	 */
	public String toString() {
		return "Id: " + id + "\nName: " + name; 
	}
}	
