package certificate;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class PrepaidCardTest {
	
	private PrepaidCard cardUnderTest;
	
	@Before
	public void setUp() throws Exception {
		cardUnderTest = new PrepaidCard("AAAAAAAA", 18.5);
	}

	@Test
	public void testPay() throws ClassNotFoundException, SQLException {
		boolean expecteds[] = {false, false};
		boolean actuals[] = {
				cardUnderTest.pay(),
				cardUnderTest.pay()
		};
		assertArrayEquals(expecteds, actuals);
	}

}
