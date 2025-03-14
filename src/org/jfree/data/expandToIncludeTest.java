package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class expandToIncludeTest {
	private Range range;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		range = new Range(1.0, 10.0);
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testExpandToInclude_ValueInsideRange() {
	    // Test: Expanding a range with a value that is already inside the range
	    Range newRange = Range.expandToInclude(range, 7.0);

	    // Since 7.0 is inside the range, the bounds should remain unchanged
	    assertEquals("Lower bound should remain unchanged", range.getLowerBound(), newRange.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should remain unchanged", range.getUpperBound(), newRange.getUpperBound(), 0.0001);
	}

	@Test
	public void testExpandToInclude_ValueExpandsLowerBound() {
	    // Test: Expanding a range with a value smaller than the current lower bound
	    Range newRange = Range.expandToInclude(range, 3.0);

	    // Lower bound should now be 3.0, while the upper bound remains the same
	    assertEquals("Lower bound should be expanded to 3.0", 3.0, newRange.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should remain unchanged", range.getUpperBound(), newRange.getUpperBound(), 0.0001);
	}

	@Test
	public void testExpandToInclude_ValueExpandsUpperBound() {
	    // Test: Expanding a range with a value greater than the current upper bound
	    Range newRange = Range.expandToInclude(range, 12.0);

	    // Lower bound remains unchanged, while the upper bound extends to 12.0
	    assertEquals("Lower bound should remain unchanged", range.getLowerBound(), newRange.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should be expanded to 12.0", 12.0, newRange.getUpperBound(), 0.0001);
	}

	@Test
	public void testExpandToInclude_NullRange() {
	    // Test: Expanding a null range should create a new range with the single value
	    Range newRange = Range.expandToInclude(null, 8.0);

	    // Since there was no previous range, the new range should have 8.0 as both bounds
	    assertEquals("New range lower bound should be 8.0", 8.0, newRange.getLowerBound(), 0.0001);
	    assertEquals("New range upper bound should be 8.0", 8.0, newRange.getUpperBound(), 0.0001);
	}

}
