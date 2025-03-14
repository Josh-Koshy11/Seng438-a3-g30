package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RangeGetCentralValueTest {

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
	public void testGetCentralValue_PositiveBounds() {
	    // Test: Central value of a range with positive lower and upper bounds
	    Range range = new Range(2.0, 10.0);
	    double centralValue = range.getCentralValue();

	    // Expected central value: (2.0 + 10.0) / 2 = 6.0
	    assertEquals("Central value of range (2.0, 10.0) should be 6.0", 
	                 6.0, centralValue, 0.0001);
	}

	@Test
	public void testGetCentralValue_NegativeBounds() {
	    // Test: Central value of a range with negative lower and upper bounds
	    Range range = new Range(-10.0, -2.0);
	    double centralValue = range.getCentralValue();

	    // Expected central value: (-10.0 + (-2.0)) / 2 = -6.0
	    assertEquals("Central value of range (-10.0, -2.0) should be -6.0", 
	                 -6.0, centralValue, 0.0001);
	}

	@Test
	public void testGetCentralValue_ZeroCrossingRange() {
	    // Test: Central value of a range crossing zero
	    Range range = new Range(-5.0, 5.0);
	    double centralValue = range.getCentralValue();

	    // Expected central value: (-5.0 + 5.0) / 2 = 0.0
	    assertEquals("Central value of range (-5.0, 5.0) should be 0.0", 
	                 0.0, centralValue, 0.0001);
	}

	@Test
	public void testGetCentralValue_LowerBoundEqualsUpperBound() {
	    // Test: Central value of a range where lower and upper bounds are the same
	    Range range = new Range(3.0, 3.0);
	    double centralValue = range.getCentralValue();

	    // Expected central value: (3.0 + 3.0) / 2 = 3.0
	    assertEquals("Central value of range (3.0, 3.0) should be 3.0", 
	                 3.0, centralValue, 0.0001);
	}


}
