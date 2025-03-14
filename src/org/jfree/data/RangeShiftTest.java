package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RangeShiftTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testShift_PositiveDelta() {
	    // Test: Shifting a range by a positive delta
	    Range base = new Range(1.0, 5.0);
	    Range shifted = Range.shift(base, 3.0);

	    // Expected range: (1.0 + 3.0, 5.0 + 3.0) = (4.0, 8.0)
	    assertEquals("Lower bound should be 4.0", 4.0, shifted.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should be 8.0", 8.0, shifted.getUpperBound(), 0.0001);
	}

	@Test
	public void testShift_NegativeDelta() {
	    // Test: Shifting a range by a negative delta
	    Range base = new Range(2.0, 6.0);
	    Range shifted = Range.shift(base, -2.0);

	    // Expected range: (2.0 - 2.0, 6.0 - 2.0) = (0.0, 4.0)
	    assertEquals("Lower bound should be 0.0", 0.0, shifted.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should be 4.0", 4.0, shifted.getUpperBound(), 0.0001);
	}

	@Test
	public void testShift_ZeroDelta() {
	    // Test: Shifting a range by zero should not change its bounds
	    Range base = new Range(-3.0, 3.0);
	    Range shifted = Range.shift(base, 0.0);

	    // Expected range remains the same: (-3.0, 3.0)
	    assertEquals("Lower bound should remain -3.0", -3.0, shifted.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should remain 3.0", 3.0, shifted.getUpperBound(), 0.0001);
	}

	@Test
	public void testShift_ZeroCrossingPrevention() {
	    // Test: Shifting a range that crosses zero
	    Range base = new Range(-2.0, 2.0);
	    Range shifted = Range.shift(base, 3.0);

	    // Expected range: (-2.0 + 3.0, 2.0 + 3.0) = (1.0, 5.0)
	    assertEquals("Lower bound should be 1.0", 1.0, shifted.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should be 5.0", 5.0, shifted.getUpperBound(), 0.0001);
	}

	@Test(expected = NullPointerException.class)
	public void testShift_NullBaseRange() {
	    // Test: Shifting a null range should throw a NullPointerException
	    Range.shift(null, 5.0);
	}

}
