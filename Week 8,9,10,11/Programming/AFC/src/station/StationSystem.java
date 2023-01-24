package station;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectToMySQL;

/**
 * @author thovi
 * This class's job is create connection to the database. Will be inherited by other helper classes that define the rule of calculating price and distance. 
 */
public class StationSystem{
	List<Station> stations = new ArrayList<Station>();
	
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
