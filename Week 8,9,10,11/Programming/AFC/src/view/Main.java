package view;

import java.sql.SQLException;
import java.util.Scanner;

import hust.soict.se.customexception.InvalidIDException;
import hust.soict.se.scanner.CardScanner;
import hust.soict.se.gate.Gate;
import hust.soict.se.recognizer.TicketRecognizer;

/**
 * @author thovi
 * This is AFCInterface.
 */
public class Main{
	
	public static void main(String[] args) throws InvalidIDException, ClassNotFoundException, SQLException, InterruptedException {
		int stationId;
		int scanOption;
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		Gate gate = Gate.getInstance();
		do { 
			do {
				System.out.println("Station ID: ");
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
