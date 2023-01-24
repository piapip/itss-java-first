package certificate;

public class Config {
	
	public static final String dbUsername = "test";
	public static final String dbPassword = "test";
	
	public static final int ONEWAY_TYPE = 1;
	public static final int HOUR24_TYPE = 2;
	public static final int PREPAID_TYPE = 3;
	
	public static final double PREPAID_MINIMUM_BALANCE = 2.5;
	public static final double BASED_FARE = 1.9;
	public static final double BASED_DISTANCE = 5.0;
	public static final double DEFAULT_ADDITION_DISTANCE = 2.0;
	//0.4euro for every extra 2 km, only round up.
	public static final double ADDITIONAL_FARE = 0.4; 
	
	//status for transaction
	public static final int UNUSED = 0;
	public static final int PENDING = 1;
	public static final int SUCCESSFUL = 2;
	public static final int EXPIRED = 3; //cho ticket
	
	//status for one way ticket
	public static final int NEW = 0;
	public static final int IN_STATION = 1;
	public static final int DESTROYED = 2;
}
