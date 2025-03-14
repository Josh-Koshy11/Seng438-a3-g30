package org.jfree.data;

import static org.junit.Assert.*; 

import org.junit.Before;
import org.junit.Test;

public class RangeTest {

    private Range range;

    @Before
    public void setUp() {
        range = new Range(1.0, 10.0);
    }

    @Test
    public void testContains_ValueWithinRange() {
        // Test: A value inside the range should return true
        assertTrue("Value 5.0 should be within the range.", range.contains(5.0));
    }

    @Test
    public void testContains_ValueLessThanLowerBound() {
        // Test: A value less than the lower bound should return false
        assertFalse("Value 0.5 should be less than the lower bound.", range.contains(0.5));
    }

    @Test
    public void testContains_ValueGreaterThanUpperBound() {
        // Test: A value greater than the upper bound should return false
        assertFalse("Value 10.5 should be greater than the upper bound.", range.contains(10.5));
    }

    @Test
    public void testContains_ValueAtLowerBound() {
        // Test: A value exactly at the lower bound should return true
        assertTrue("Value 1.0 should be exactly at the lower bound.", range.contains(1.0));
    }

}

