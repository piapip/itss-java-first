package station;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StationHelperTest {
	
	private StationHelper classUnderTest;

	@Before
	public void setUp() throws Exception {
		classUnderTest = new StationHelper();
	}

	@Test
	public void testCalculateFare() {
		double expecteds[] = {1.9, 3.5}; 
		double actuals[] = {
				classUnderTest.calculateFare(2, 3),
				classUnderTest.calculateFare(4, 1)
		};
		assertArrayEquals(expecteds, actuals, 0.01);
	}
	@Test
	public void testGetLowestCost() {
		double expecteds[] = {1.9, 1.9, 1.9};
		double actuals[] = {
				classUnderTest.getLowestCost(1),
				classUnderTest.getLowestCost(4),
				classUnderTest.getLowestCost(9)
		};
		assertArrayEquals(expecteds, actuals, 0.01);
	}

}
