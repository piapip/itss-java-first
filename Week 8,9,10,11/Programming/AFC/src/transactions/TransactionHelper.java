package transactions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectToMySQL;

/**
 * @author thovi
 * This class contains functions that create to the transaction's database and perform UPDATE and READ actions. 
 */
public class TransactionHelper {
	
	private Connection ConnectToTravelCertificateDB() throws ClassNotFoundException, SQLException {
		return ConnectToMySQL.getInformation("transaction_history");
	}
	
	public TransactionHistory getTransactionByTransId(int ID) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToTravelCertificateDB();
		Statement statement = connection.createStatement();
		String sql = "Select * from transactions WHERE id=" + ID;
		ResultSet rs = statement.executeQuery(sql);
		if (rs != null) {
			connection.close();
			while(rs.next()) {
				String certificateId = rs.getString(2);
				int status = rs.getInt(3);
				String dayIn = rs.getDate(4) + " " + rs.getTime(4);
				String dayOut = rs.getDate(5) + " " + rs.getTime(5); 
				int embarkingStationID = rs.getInt(6);
				int endingStationID = rs.getInt(7);	
				return new TransactionHistory(ID, certificateId, status, dayIn, dayOut, embarkingStationID, endingStationID);
			}			
		}
		connection.close();
		return null;
	}
	
	public List<TransactionHistory> getTransactionByCertificateId(String ID) throws SQLException, ClassNotFoundException {
		List<TransactionHistory> result = new ArrayList<TransactionHistory>();
		Connection connection = ConnectToTravelCertificateDB();
		Statement statement = connection.createStatement();
		String sql = "Select * from transactions WHERE certificateID='" + ID +"'";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			int transactionID = rs.getInt(1);
			String certificateId = rs.getString(2);
			int status = rs.getInt(3);
			String dayIn = rs.getDate(4) + " " + rs.getTime(4);
			String dayOut = rs.getDate(5) + " " + rs.getTime(5); 
			int embarkingStationID = rs.getInt(6);
			int endingStationID = rs.getInt(7);
			result.add(new TransactionHistory(transactionID, certificateId, status, dayIn, dayOut, embarkingStationID, endingStationID));
		}	
		connection.close();
		return result;
	}	
	
	public void updateTransaction(int ID, TransactionHistory newTransaction) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectToTravelCertificateDB();
		Statement statement = connection.createStatement();
		String sql = "UPDATE transactions SET status=" + newTransaction.getStatus()+" WHERE id=\"" + newTransaction.getId()+ "\"";
		statement.executeUpdate(sql);	
		if(newTransaction.getEmbarkingStationID() != 0) {
			sql = "UPDATE transactions SET embarking_station_ID=\"" + newTransaction.getEmbarkingStationID()+"\" WHERE id=\"" + newTransaction.getId()+ "\"";
			statement.executeUpdate(sql);	
		}
		if(newTransaction.getEndingStationID() != 0) {
			sql = "UPDATE transactions SET ending_station_ID=\"" + newTransaction.getEndingStationID()+"\" WHERE id=\"" + newTransaction.getId()+ "\"";
			statement.executeUpdate(sql);	
		}
		if(!newTransaction.getDayIn().equals("null null")) {
			sql = "UPDATE transactions SET time_In=\"" + newTransaction.getDayIn() +"\" WHERE id=\"" + newTransaction.getId()+ "\"";
			statement.executeUpdate(sql);
		}
		if(!newTransaction.getDayOut().equals("null null")) {
			sql = "UPDATE transactions SET time_Out=\"" + newTransaction.getDayOut() +"\" WHERE id=\"" + newTransaction.getId()+ "\"";
			statement.executeUpdate(sql);
		}
		connection.close();
	}
	
	public void addTransaction(String certificateId) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectToMySQL.getInformation("transaction_history");
		Statement statement = connection.createStatement();
		String sql = "INSERT INTO `transactions` (certificateID, status) VALUES('" + certificateId + "', '0');";
		statement.executeUpdate(sql);
		connection.close();
	}
}
