package checkValid;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class PrepaidCardTest {
		
	private AFCCheckValidOfPrepaidCard classUnderTest;
	
	@Before
	public void setUp() throws Exception {
		classUnderTest = new AFCCheckValidOfPrepaidCard();
	}

	@Test
	public void testIsValidEnter() throws ClassNotFoundException, SQLException {		
		String cardShouldPass = "c34ab6abb7b2bb59";
		String notExist = "9999999999999999";
		String notHaveMoney = "acea9bbeb39f5ff3";
		
		String expecteds[] = {
				null,  
				"Card doesn't exist. Please buy a new one.",
				"Card's balance is too low. Please recharge: 1.9"
		};
		String actuals[] = {
				classUnderTest.isValidEnter(cardShouldPass, 4),
				classUnderTest.isValidEnter(notExist, 4),
				classUnderTest.isValidEnter(notHaveMoney, 4)
		}; 
		assertArrayEquals(expecteds, actuals);
	}
}
