package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class createNumberArrayTest extends DataUtilities {

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
	public void testCreateNumberArray_PositiveNumbers() {
	    // Testing conversion of an array with positive numbers
	    double[] input = {1.0, 2.5, 3.8};

	    // Convert the double[] array to a Number[] array
	    Number[] result = DataUtilities.createNumberArray(input);

	    // Ensure all values are correctly wrapped in Number objects
	    assertArrayEquals(new Number[]{1.0, 2.5, 3.8}, result);
	}

	@Test
	public void testCreateNumberArray_NegativeNumbers() {
	    // Testing conversion of an array with negative numbers
	    double[] input = {-1.0, -2.5, -3.8};

	    // Convert and check that negative values are handled correctly
	    Number[] result = DataUtilities.createNumberArray(input);

	    assertArrayEquals(new Number[]{-1.0, -2.5, -3.8}, result);
	}

	@Test
	public void testCreateNumberArray_MixedNumbers() {
	    // Testing a mix of positive, negative, and zero values
	    double[] input = {-1.0, 0.0, 3.5};

	    // Convert and verify correct representation
	    Number[] result = DataUtilities.createNumberArray(input);

	    assertArrayEquals(new Number[]{-1.0, 0.0, 3.5}, result);
	}

	@Test
	public void testCreateNumberArray_EmptyArray() {
	    // Testing an empty array (edge case)
	    double[] input = {};

	    // Convert and ensure output is also an empty array
	    Number[] result = DataUtilities.createNumberArray(input);

	    assertArrayEquals(new Number[]{}, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateNumberArray_NullArray() {
	    // Passing null should trigger an IllegalArgumentException
	    DataUtilities.createNumberArray(null);
	}


}
