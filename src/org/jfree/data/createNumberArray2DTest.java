package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class createNumberArray2DTest {

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
	public void testCreateNumberArray2D_PositiveNumbers() {
	    // Testing conversion of a 2D array with positive numbers
	    double[][] input = {{1.0, 2.0}, {3.0, 4.0}};

	    // Call the method to convert the double[][] array to Number[][]
	    Number[][] result = DataUtilities.createNumberArray2D(input);

	    // Expect the same structure but wrapped in Number objects
	    assertArrayEquals(new Number[][]{{1.0, 2.0}, {3.0, 4.0}}, result);
	}

	@Test
	public void testCreateNumberArray2D_NegativeNumbers() {
	    // Testing conversion of a 2D array with negative numbers
	    double[][] input = {{-1.0, -2.0}, {-3.0, -4.0}};

	    // Convert and check if negative values are handled correctly
	    Number[][] result = DataUtilities.createNumberArray2D(input);

	    assertArrayEquals(new Number[][]{{-1.0, -2.0}, {-3.0, -4.0}}, result);
	}

	@Test
	public void testCreateNumberArray2D_MixedNumbers() {
	    // Testing a mix of positive, negative, and zero values
	    double[][] input = {{-1.0, 0.0}, {3.5, -2.5}};

	    // Convert and verify correct representation
	    Number[][] result = DataUtilities.createNumberArray2D(input);

	    assertArrayEquals(new Number[][]{{-1.0, 0.0}, {3.5, -2.5}}, result);
	}

	@Test
	public void testCreateNumberArray2D_EmptyArray() {
	    // Testing an empty 2D array (edge case)
	    double[][] input = {};

	    // Convert and ensure output is also an empty 2D array
	    Number[][] result = DataUtilities.createNumberArray2D(input);

	    assertArrayEquals(new Number[][]{}, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateNumberArray2D_NullArray() {
	    // Passing null should trigger an IllegalArgumentException
	    DataUtilities.createNumberArray2D(null);
	}


}
