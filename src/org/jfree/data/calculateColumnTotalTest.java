package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jmock.Expectations;
import org.jmock.Mockery;
public class calculateColumnTotalTest {
	 private Mockery mockingContext;
	   private Values2D values;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		 mockingContext = new Mockery();
	        values = mockingContext.mock(Values2D.class);
	}

	@After
	public void tearDown() throws Exception {
		 mockingContext = null;
	        values = null;
	}

	@Test
	public void testCalculateColumnTotal_ValidColumn() {
	    // Setting up mock expectations for a 3-row table
	    mockingContext.checking(new Expectations() {{
	        one(values).getRowCount(); will(returnValue(3)); // Table has 3 rows
	        one(values).getValue(0, 0); will(returnValue(5.0)); // First row, column 0 = 5.0
	        one(values).getValue(1, 0); will(returnValue(3.0)); // Second row, column 0 = 3.0
	        one(values).getValue(2, 0); will(returnValue(2.0)); // Third row, column 0 = 2.0
	    }});

	    // Calling the method under test
	    double result = DataUtilities.calculateColumnTotal(values, 0); 

	    // The expected sum is 5.0 + 3.0 + 2.0 = 10.0
	    assertEquals(10.0, result, 0.000000001);
	}

	@Test 
	public void testCalculateColumnTotal_OutOfBoundsColumnNegative() {
	    // Creating a fresh mock context and mock object for this test
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);

	    mockingContext.checking(new Expectations() {{
	        one(values).getRowCount(); 
	        will(returnValue(2)); // Assume table has 2 rows

	        allowing(values).getValue(with(any(Integer.class)), with(equal(-1))); 
	        will(returnValue(null)); // If an invalid column (-1) is accessed, return null
	    }});

	    // Calling the method with an invalid (negative) column index
	    double result = DataUtilities.calculateColumnTotal(values, -1);

	    // Expecting 0.0 because invalid column access should not contribute to sum
	    assertEquals(0.0, result, 0.000000001);
	}

	@Test
	public void testCalculateColumnTotal_EmptyData() {
	    // Mocking a table with 0 rows (empty table)
	    mockingContext.checking(new Expectations() {{
	        one(values).getRowCount(); will(returnValue(0));
	    }});

	    // Calling the method with an empty dataset
	    double result = DataUtilities.calculateColumnTotal(values, 0);

	    // Sum of an empty column should be 0.0
	    assertEquals(0.0, result, 0.000000001);
	}
//
////	@Test(expected = NullPointerException.class)
////	public void testCalculateColumnTotal_NullData() {
////	    // Directly passing null as the table to see if it throws a NullPointerException
////	    DataUtilities.calculateColumnTotal(null, 0);
////	}

	@Test
	public void testCalculateColumnTotal_BoundaryFirstColumn() {
	    // Mocking a table with 2 rows, testing the first column (index 0)
	    mockingContext.checking(new Expectations() {{
	        one(values).getRowCount(); will(returnValue(2)); // Table has 2 rows
	        one(values).getValue(0, 0); will(returnValue(4.0)); // First row, column 0 = 4.0
	        one(values).getValue(1, 0); will(returnValue(6.0)); // Second row, column 0 = 6.0
	    }});

	    // Calling the method on the first column
	    double result = DataUtilities.calculateColumnTotal(values, 0);

	    // The expected sum is 4.0 + 6.0 = 10.0
	    assertEquals(10.0, result, 0.000000001);
	}

	@Test
	public void testCalculateColumnTotal_BoundaryLastColumn() {
	    // Mocking a table with 2 rows, testing the last column (index 1)
	    mockingContext.checking(new Expectations() {{
	        one(values).getRowCount(); will(returnValue(2)); // Table has 2 rows
	        one(values).getValue(0, 1); will(returnValue(4.0)); // First row, column 1 = 4.0
	        one(values).getValue(1, 1); will(returnValue(6.0)); // Second row, column 1 = 6.0
	    }});

	    // Calling the method on the last column
	    double result = DataUtilities.calculateColumnTotal(values, 1);

	    // The expected sum is 4.0 + 6.0 = 10.0
	    assertEquals(10.0, result, 0.000000001);
	}

}
