package view;

import java.sql.SQLException;
import java.util.Scanner;

import hust.soict.se.customexception.InvalidIDException;
import hust.soict.se.scanner.CardScanner;
import hust.soict.se.gate.Gate;
import hust.soict.se.recognizer.TicketRecognizer;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 *
 * @author Pham Huu Tho
 * This is AFCInterface.
 */
public class Main{
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws InvalidIDException the invalid ID exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String[] args) throws InvalidIDException, ClassNotFoundException, SQLException, InterruptedException {
		int stationId;
		int scanOption;
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		Gate gate = Gate.getInstance();
		do { 
			do {
				System.out.println("\nThese are stations in the line M14 of Paris:\r\n" + 
						"1. Saint-Lazare\r\n" + 
						"2. Madeleine\r\n" + 
						"3. Pyramides\r\n" + 
						"4. Chatelet\r\n" + 
						"5. Gare de Lyon\r\n" + 
						"6. Bercy\r\n" + 
						"7. Cour Saint-Emilion\r\n" + 
						"8. Bibliotheque Francois Mitterrand\r\n" + 
						"9. Olympiades\r\n" +
						"Station ID: ");
				stationId = reader.nextInt();
				reader.nextLine();
			} while(stationId > 9 || stationId < 1);
			
			do {
				System.out.println("What are you using?:\n1. Card\n2. Ticket");
				scanOption = reader.nextInt();
				reader.nextLine();
			} while(scanOption > 2 || scanOption < 1);
			
			System.out.println("Please enter your barcode: ");
			String barCode = reader.nextLine();
			
			TicketRecognizer ticketRecognizer = TicketRecognizer.getInstance();
			CardScanner cardScanner = CardScanner.getInstance();
			
			String certificateId;
			if(scanOption == 1) {
				certificateId = cardScanner.process(barCode);
			} else {
				certificateId = ticketRecognizer.process(barCode);
			}
			
			String error = AFCController.getValid(certificateId, stationId);
			if (error != null) {
				System.out.println("ERRRRRRR!");
				System.out.println(error);
			} else {
				gate.open();
				Thread.sleep(2000);
				gate.close();
			}
		} while (true);
	}
}
