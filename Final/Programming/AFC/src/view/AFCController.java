package view;

import java.sql.SQLException;

import certificate.*;
import checkValid.*;
import transactions.TransactionHistory;

/**
 * @author Pham Huu Tho
 * The Class AFCController.
 */
public class AFCController {
	
	/**
	 * Gets the validation of the certificate with the corresponding Id.
	 *
	 * @param certificateId the certificate id
	 * @param stationID the station ID
	 * @return the valid
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public static String getValid(String certificateId, int stationID) throws ClassNotFoundException, SQLException {
		
		TravelCertificateStorage certificateStorage = new TravelCertificateStorage();
		TravelCertificateInformation certificate = certificateStorage.getCertificateById(certificateId);
		if(certificate == null) return "Certificate doesn't exist!";
		TransactionHistory currentTrip = certificate.getCurrentTrip();
		int option = certificate.getType();
		AFCCheckValidOfCertificate check;
		CertificateHelper helper;
		
		if(option == 1) {
			check = new AFCCheckValidOfOneway();
			helper = new OnewayTicketHelper();
		} else if (option == 2) {
			check = new AFCCheckValidOfHour24();
			helper = new Hour24TicketHeper();
		} else {
			check = new AFCCheckValidOfPrepaidCard();
			helper = new PrepaidCardHelper();
		}
		
		String error = null;
		if (currentTrip == null || currentTrip.getStatus() == Config.UNUSED) { 
			error = check.isValidEnter(certificateId, stationID);
		} else {
			error = check.isValidExit(certificateId, stationID);
		}
		if(error != null) return error;
		else {
			helper.markStation(certificateId, stationID);
		}		
		return null;
	}
}
