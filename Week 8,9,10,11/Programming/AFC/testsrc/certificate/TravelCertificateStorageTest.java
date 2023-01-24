package certificate;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class TravelCertificateStorageTest {
	
	private TravelCertificateStorage classUnderTest;

	@Before
	public void setUp() throws Exception {
		classUnderTest = new TravelCertificateStorage();
	}

	@Test
	public void testGetTypeById() throws ClassNotFoundException, SQLException {
		int expecteds[] = {3,1,1,-1};
		int actuals[] = { classUnderTest.getTypeById("2f858775d71cc4ec"), 
						  classUnderTest.getTypeById("50221250d5a5d20c"),
						  classUnderTest.getTypeById("7885fd6de6fc9a36"),
						  classUnderTest.getTypeById("should fail this one") };
		assertArrayEquals(expecteds, actuals);
	}

}
