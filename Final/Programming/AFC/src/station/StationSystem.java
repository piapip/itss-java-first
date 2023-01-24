package station;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectToMySQL;

// TODO: Auto-generated Javadoc
/**
 * The Class StationSystem.
 *
 * @author Pham Huu Tho
 * This class's job is create connection to the database. Will be inherited by other helper classes that define the rule of calculating price and distance.
 */
public class StationSystem{
	
	/** The stations. */
	List<Station> stations = new ArrayList<Station>();
	
	/**
	 * Instantiates a new station system.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	//CONNECT TO THE DATABASE
	public StationSystem() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToMySQL.getInformation("station_system");
		Statement statement = connection.createStatement();
		String sql = "Select * from station";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			int stationId = rs.getInt(1);
			String stationName = rs.getString(2);
			double stationDistance = rs.getDouble(3);
			this.stations.add(new Station(stationId, stationName, stationDistance));
		}
		connection.close();
	}
}
