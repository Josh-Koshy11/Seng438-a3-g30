package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RangeCombineTest {

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
	public void testCombine_NonOverlappingRanges() {
	    // Test: Combining two non-overlapping ranges
	    Range range1 = new Range(1.0, 5.0);
	    Range range2 = new Range(6.0, 10.0);

	    try {
	        Range combined = Range.combine(range1, range2);

	        // Ensure the combined range is not null
	        assertNotNull("Combined range should not be null", combined);

	        // Verify the lower and upper bounds
	        assertEquals("Lower bound should be 1.0", 1.0, combined.getLowerBound(), 0.0001);
	        assertEquals("Upper bound should be 10.0", 10.0, combined.getUpperBound(), 0.0001);
	    } catch (IllegalArgumentException e) {
	        fail("Range.combine() should not throw an IllegalArgumentException for valid ranges.");
	    }
	}

	@Test
	public void testCombine_OverlappingRanges() {
	    // Test: Combining two overlapping ranges
	    Range range1 = new Range(1.0, 7.0);
	    Range range2 = new Range(5.0, 10.0);

	    try {
	        Range combined = Range.combine(range1, range2);

	        // Ensure the combined range is not null
	        assertNotNull("Combined range should not be null", combined);

	        // Verify the new range includes the full span of both ranges
	        assertEquals("Lower bound should be 1.0", 1.0, combined.getLowerBound(), 0.0001);
	        assertEquals("Upper bound should be 10.0", 10.0, combined.getUpperBound(), 0.0001);
	    } catch (IllegalArgumentException e) {
	        fail("Range.combine() threw an IllegalArgumentException: " + e.getMessage());
	    }
	}

	@Test
	public void testCombine_OneNullRange() {
	    // Test: Combining one null range with a valid range
	    Range range = new Range(5.0, 10.0);
	    
	    // Case 1: First range is null
	    Range combined1 = Range.combine(null, range);
	    assertNotNull("Combined range should be the non-null range", combined1);
	    assertEquals("Lower bound should be 5.0", 5.0, combined1.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should be 10.0", 10.0, combined1.getUpperBound(), 0.0001);
	    
	    // Case 2: Second range is null
	    Range combined2 = Range.combine(range, null);
	    assertNotNull("Combined range should be the non-null range", combined2);
	    assertEquals("Lower bound should be 5.0", 5.0, combined2.getLowerBound(), 0.0001);
	    assertEquals("Upper bound should be 10.0", 10.0, combined2.getUpperBound(), 0.0001);
	}

	@Test
	public void testCombine_BothNullRanges() {
	    // Test: Combining two null ranges should return null
	    Range combined = Range.combine(null, null);
	    assertNull("Combined range should be null when both inputs are null", combined);
	}

}
