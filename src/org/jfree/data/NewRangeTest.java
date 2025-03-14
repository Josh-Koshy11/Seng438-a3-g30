package org.jfree.data;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;

public class NewRangeTest {

    private Range range;

    @Before
    public void setUp() {
        range = new Range(1.0, 10.0);
    }

    // ---------- Existing tests for contains() ----------
    @Test
    public void testContains_ValueWithinRange() {
        assertTrue("Value 5.0 should be within the range.", range.contains(5.0));
    }

    @Test
    public void testContains_ValueLessThanLowerBound() {
        assertFalse("Value 0.5 should be less than the lower bound.", range.contains(0.5));
    }

    @Test
    public void testContains_ValueGreaterThanUpperBound() {
        assertFalse("Value 10.5 should be greater than the upper bound.", range.contains(10.5));
    }

    @Test
    public void testContains_ValueAtLowerBound() {
        assertTrue("Value 1.0 should be exactly at the lower bound.", range.contains(1.0));
    }
    
    // New test: value exactly at the upper bound.
    @Test
    public void testContains_ValueAtUpperBound() {
        assertTrue("Value 10.0 should be exactly at the upper bound.", range.contains(10.0));
    }

    // ---------- Tests for constructor ----------
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidRange() {
        new Range(10.0, 1.0);
    }

    // ---------- Tests for basic getters ----------
    @Test
    public void testGetLowerBound() {
        assertEquals(1.0, range.getLowerBound(), 0.0000001);
    }

    @Test
    public void testGetUpperBound() {
        assertEquals(10.0, range.getUpperBound(), 0.0000001);
    }

    @Test
    public void testGetLength() {
        assertEquals(9.0, range.getLength(), 0.0000001);
    }

    @Test
    public void testGetCentralValue() {
        // Expected central value = (1.0/2 + 10.0/2) = 5.5
        assertEquals(5.5, range.getCentralValue(), 0.0000001);
    }

    // ---------- Tests to cover defensive checks in getters using reflection ----------
    @Test(expected = IllegalArgumentException.class)
    public void testGetLowerBound_InvalidRange() throws Exception {
        Range r = new Range(1.0, 10.0);
        Field lowerField = Range.class.getDeclaredField("lower");
        lowerField.setAccessible(true);
        // Force an invalid state: set lower > upper.
        lowerField.set(r, 15.0);
        r.getLowerBound(); // should throw IllegalArgumentException
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetUpperBound_InvalidRange() throws Exception {
        Range r = new Range(1.0, 10.0);
        Field lowerField = Range.class.getDeclaredField("lower");
        lowerField.setAccessible(true);
        lowerField.set(r, 15.0);
        r.getUpperBound(); // should throw IllegalArgumentException
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetLength_InvalidRange() throws Exception {
        Range r = new Range(1.0, 10.0);
        Field lowerField = Range.class.getDeclaredField("lower");
        lowerField.setAccessible(true);
        lowerField.set(r, 15.0);
        r.getLength(); // should throw IllegalArgumentException
    }

    // ---------- Tests for intersects(double, double) ----------
    @Test
    public void testIntersects_BelowRange() {
        // [0, 0.5] should not intersect [1,10]
        assertFalse(range.intersects(0.0, 0.5));
    }

    @Test
    public void testIntersects_AtLowerBoundary() {
        // [0, 1.5] should intersect because 1.5 > lower bound (1.0)
        assertTrue(range.intersects(0.0, 1.5));
    }

    @Test
    public void testIntersects_WithinRange() {
        // [5, 15] should intersect [1,10]
        assertTrue(range.intersects(5.0, 15.0));
    }

    @Test
    public void testIntersects_NoIntersection() {
        // [10, 20] should not intersect because 10 is not less than the upper bound
        assertFalse(range.intersects(10.0, 20.0));
    }

    // ---------- Test for intersects(Range) ----------
    @Test
    public void testIntersects_Range() {
        Range intersecting = new Range(5.0, 15.0);
        assertTrue(range.intersects(intersecting));
        
        Range nonIntersecting = new Range(10.0, 20.0);
        assertFalse(range.intersects(nonIntersecting));
    }

    // ---------- Tests for constrain(double) ----------
    @Test
    public void testConstrain_ValueWithinRange() {
        assertEquals(5.0, range.constrain(5.0), 0.0000001);
    }

    @Test
    public void testConstrain_ValueBelowRange() {
        assertEquals(1.0, range.constrain(0.0), 0.0000001);
    }

    @Test
    public void testConstrain_ValueAboveRange() {
        assertEquals(10.0, range.constrain(11.0), 0.0000001);
    }

    // ---------- Tests for combine(Range, Range) ----------
    @Test
    public void testCombine_BothNonNull() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(3.0, 10.0);
        Range combined = Range.combine(r1, r2);
        assertEquals(1.0, combined.getLowerBound(), 0.0000001);
        assertEquals(10.0, combined.getUpperBound(), 0.0000001);
    }

    @Test
    public void testCombine_FirstNull() {
        Range r2 = new Range(2.0, 8.0);
        Range combined = Range.combine(null, r2);
        assertEquals(r2, combined);
    }

    @Test
    public void testCombine_SecondNull() {
        Range r1 = new Range(2.0, 8.0);
        Range combined = Range.combine(r1, null);
        assertEquals(r1, combined);
    }

    @Test
    public void testCombine_BothNull() {
        Range combined = Range.combine(null, null);
        assertNull(combined);
    }

    // ---------- Tests for combineIgnoringNaN(Range, Range) ----------
    @Test
    public void testCombineIgnoringNaN_BothNonNull() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(3.0, 10.0);
        Range combined = Range.combineIgnoringNaN(r1, r2);
        assertEquals(1.0, combined.getLowerBound(), 0.0000001);
        assertEquals(10.0, combined.getUpperBound(), 0.0000001);
    }

    @Test
    public void testCombineIgnoringNaN_FirstNull_NonNaNSecond() {
        Range r2 = new Range(2.0, 8.0);
        Range combined = Range.combineIgnoringNaN(null, r2);
        assertEquals(r2, combined);
    }

    @Test
    public void testCombineIgnoringNaN_FirstNull_NaNSecond() {
        Range nanRange = new Range(Double.NaN, Double.NaN);
        Range combined = Range.combineIgnoringNaN(null, nanRange);
        assertNull(combined);
    }

    @Test
    public void testCombineIgnoringNaN_SecondNull_NaNFirst() {
        Range nanRange = new Range(Double.NaN, Double.NaN);
        Range combined = Range.combineIgnoringNaN(nanRange, null);
        assertNull(combined);
    }
    
    // New: Both ranges non-null with NaN values in bounds.
    @Test
    public void testCombineIgnoringNaN_BothNaN() {
        Range nanRange1 = new Range(Double.NaN, Double.NaN);
        Range nanRange2 = new Range(Double.NaN, Double.NaN);
        Range combined = Range.combineIgnoringNaN(nanRange1, nanRange2);
        assertNull(combined);
    }
    
    // New: One range has NaN lower bound.
    @Test
    public void testCombineIgnoringNaN_OneBoundNaN() {
        Range nanLower = new Range(Double.NaN, 5.0);
        Range validRange = new Range(2.0, 8.0);
        Range combined = Range.combineIgnoringNaN(nanLower, validRange);
        assertEquals(2.0, combined.getLowerBound(), 0.0000001);
        assertEquals(8.0, combined.getUpperBound(), 0.0000001);
    }
    
    // New: One range has NaN upper bound.
    @Test
    public void testCombineIgnoringNaN_UpperNaN() {
        Range nanUpper = new Range(2.0, Double.NaN);
        Range validRange = new Range(1.0, 8.0);
        Range combined = Range.combineIgnoringNaN(nanUpper, validRange);
        assertEquals(1.0, combined.getLowerBound(), 0.0000001);
        assertEquals(8.0, combined.getUpperBound(), 0.0000001);
    }

    // ---------- Tests for expandToInclude(Range, double) ----------
    @Test
    public void testExpandToInclude_NullRange() {
        Range expanded = Range.expandToInclude(null, 5.0);
        assertEquals(5.0, expanded.getLowerBound(), 0.0000001);
        assertEquals(5.0, expanded.getUpperBound(), 0.0000001);
    }

    @Test
    public void testExpandToInclude_ValueLessThanRange() {
        Range expanded = Range.expandToInclude(range, 0.0);
        assertEquals(0.0, expanded.getLowerBound(), 0.0000001);
        assertEquals(10.0, expanded.getUpperBound(), 0.0000001);
    }

    @Test
    public void testExpandToInclude_ValueGreaterThanRange() {
        Range expanded = Range.expandToInclude(range, 15.0);
        assertEquals(1.0, expanded.getLowerBound(), 0.0000001);
        assertEquals(15.0, expanded.getUpperBound(), 0.0000001);
    }

    @Test
    public void testExpandToInclude_ValueWithinRange() {
        // Value inside the range should return the same range.
        Range expanded = Range.expandToInclude(range, 5.0);
        assertEquals(range, expanded);
    }

    // ---------- Tests for expand(Range, double, double) ----------
    @Test
    public void testExpand() {
        // For range [1,10], length = 9.
        // With lowerMargin = 0.1 and upperMargin = 0.2:
        // Expected lower = 1 - (9 * 0.1) = 0.1, upper = 10 + (9 * 0.2) = 11.8
        Range expanded = Range.expand(range, 0.1, 0.2);
        assertEquals(0.1, expanded.getLowerBound(), 0.0000001);
        assertEquals(11.8, expanded.getUpperBound(), 0.0000001);
    }
    
    // New: Expand branch where computed lower > upper.
    @Test
    public void testExpand_InvertedMargins() {
        // For range [1,10] with lowerMargin = -2.0 and upperMargin = 0.0:
        // lower = 1 - 9*(-2) = 1 + 18 = 19, upper = 10 + 0 = 10.
        // Since 19 > 10, branch sets both bounds to (19/2 + 10/2) = 14.5.
        Range expanded = Range.expand(range, -2.0, 0.0);
        assertEquals(14.5, expanded.getLowerBound(), 0.0000001);
        assertEquals(14.5, expanded.getUpperBound(), 0.0000001);
    }

    // ---------- Tests for shift(Range, double, boolean) ----------
    @Test
    public void testShift_AllowZeroCrossing() {
        // Shifting [1,10] by -2 with allowZeroCrossing true:
        // Expected lower = 1 + (-2) = -1, upper = 10 + (-2) = 8
        Range shifted = Range.shift(range, -2.0, true);
        assertEquals(-1.0, shifted.getLowerBound(), 0.0000001);
        assertEquals(8.0, shifted.getUpperBound(), 0.0000001);
    }

    @Test
    public void testShift_NoZeroCrossing() {
        // With allowZeroCrossing false, shift uses shiftWithNoZeroCrossing.
        // For lower: shiftWithNoZeroCrossing(1, -2) returns max(1-2, 0) = 0.
        // For upper: shiftWithNoZeroCrossing(10, -2) returns max(10-2, 0) = 8.
        Range shifted = Range.shift(range, -2.0, false);
        assertEquals(0.0, shifted.getLowerBound(), 0.0000001);
        assertEquals(8.0, shifted.getUpperBound(), 0.0000001);
    }
    
    // New: Test the two-argument shift (calls shift with allowZeroCrossing false).
    @Test
    public void testShift_TwoArg() {
        Range shifted = Range.shift(range, 5.0);
        // For lower: shiftWithNoZeroCrossing(1,5) = max(6,0) = 6.0.
        // For upper: shiftWithNoZeroCrossing(10,5) = max(15,0) = 15.0.
        assertEquals(6.0, shifted.getLowerBound(), 0.0000001);
        assertEquals(15.0, shifted.getUpperBound(), 0.0000001);
    }
    
    // New: Test shiftWithNoZeroCrossing for a negative range.
    @Test
    public void testShift_NoZeroCrossing_NegativeRange() {
        Range negRange = new Range(-10.0, -1.0);
        Range shifted = Range.shift(negRange, 20.0, false);
        // For lower: shiftWithNoZeroCrossing(-10,20)= Math.min(10,0)=0.
        // For upper: shiftWithNoZeroCrossing(-1,20)= Math.min(19,0)=0.
        assertEquals(0.0, shifted.getLowerBound(), 0.0000001);
        assertEquals(0.0, shifted.getUpperBound(), 0.0000001);
    }
    
    // New: Test shiftWithNoZeroCrossing for a value of 0.
    @Test
    public void testShift_WithZeroValue_NoZeroCrossing() {
        Range zeroRange = new Range(0.0, 5.0);
        Range shifted = Range.shift(zeroRange, -1.0, false);
        // For lower: value==0, so returns 0+(-1)= -1.
        // For upper: 5>0, so returns Math.max(5-1,0)=4.
        assertEquals(-1.0, shifted.getLowerBound(), 0.0000001);
        assertEquals(4.0, shifted.getUpperBound(), 0.0000001);
    }

    // ---------- Tests for scale(Range, double) ----------
    @Test
    public void testScale_PositiveFactor() {
        // For range [1,10] with factor 2: expected range [2,20]
        Range scaled = Range.scale(range, 2.0);
        assertEquals(2.0, scaled.getLowerBound(), 0.0000001);
        assertEquals(20.0, scaled.getUpperBound(), 0.0000001);
    }

    @Test
    public void testScale_FactorZero() {
        // With factor 0, expected range becomes [0,0]
        Range scaled = Range.scale(range, 0.0);
        assertEquals(0.0, scaled.getLowerBound(), 0.0000001);
        assertEquals(0.0, scaled.getUpperBound(), 0.0000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScale_NegativeFactor() {
        Range.scale(range, -1.0);
    }

    // ---------- Tests for equals ----------
    @Test
    public void testEquals_SameRange() {
        Range same = new Range(1.0, 10.0);
        assertTrue(range.equals(same));
    }

    @Test
    public void testEquals_DifferentRange() {
        Range diff = new Range(2.0, 10.0);
        assertFalse(range.equals(diff));
    }

    @Test
    public void testEquals_Null() {
        assertFalse(range.equals(null));
    }

    @Test
    public void testEquals_DifferentObject() {
        assertFalse(range.equals("Not a range"));
    }

    // ---------- Test for hashCode consistency ----------
    @Test
    public void testHashCode_Consistency() {
        Range same = new Range(1.0, 10.0);
        assertEquals(range.hashCode(), same.hashCode());
    }

    // ---------- Test for toString ----------
    @Test
    public void testToString() {
        String expected = "Range[1.0,10.0]";
        assertEquals(expected, range.toString());
    }

    // ---------- Tests for isNaNRange ----------
    @Test
    public void testIsNaNRange_False() {
        assertFalse(range.isNaNRange());
    }

    @Test
    public void testIsNaNRange_True() {
        Range nanRange = new Range(Double.NaN, Double.NaN);
        assertTrue(nanRange.isNaNRange());
    }
}