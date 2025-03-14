package org.jfree.data;

import static org.junit.Assert.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class calculateRowTotalTest {
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
	public void testCalculateRowTotal_ValidRow() {
	    // Mocking a table with 3 columns in a row (row index 0)
	    mockingContext.checking(new Expectations() {{
	        one(values).getColumnCount(); will(returnValue(3)); // Table has 3 columns
	        one(values).getValue(0, 0); will(returnValue(2.0)); // Row 0, Col 0 = 2.0
	        one(values).getValue(0, 1); will(returnValue(3.0)); // Row 0, Col 1 = 3.0
	        one(values).getValue(0, 2); will(returnValue(5.0)); // Row 0, Col 2 = 5.0
	    }});

	    // Calling the method on row 0
	    double result = DataUtilities.calculateRowTotal(values, 0);

	    // The expected sum is 2.0 + 3.0 + 5.0 = 10.0
	    assertEquals(10.0, result, 0.000000001);
	}

	@Test
	public void testCalculateRowTotal_EmptyData() {
	    // Mocking a table with 0 columns (empty row)
	    mockingContext.checking(new Expectations() {{
	        one(values).getColumnCount(); will(returnValue(0));
	    }});

	    // Calling the method on an empty row
	    double result = DataUtilities.calculateRowTotal(values, 0);

	    // Sum of an empty row should be 0.0
	    assertEquals(0.0, result, 0.000000001);
	}

//	@Test(expected = NullPointerException.class)
//	public void testCalculateRowTotal_NullData() {
//	    // Directly passing null to check if it throws a NullPointerException
//	    DataUtilities.calculateRowTotal(null, 0);
//	}

	@Test
	public void testCalculateRowTotal_BoundaryFirstRow() {
	    // Mocking a table where we test the first row (index 0)
	    mockingContext.checking(new Expectations() {{
	        one(values).getColumnCount(); will(returnValue(2)); // Table has 2 columns
	        one(values).getValue(0, 0); will(returnValue(3.0)); // Row 0, Col 0 = 3.0
	        one(values).getValue(0, 1); will(returnValue(4.0)); // Row 0, Col 1 = 4.0
	    }});

	    // Calling the method on the first row
	    double result = DataUtilities.calculateRowTotal(values, 0);

	    // The expected sum is 3.0 + 4.0 = 7.0
	    assertEquals(7.0, result, 0.000000001);
	}

	@Test
	public void testCalculateRowTotal_BoundaryLastRow() {
	    // Mocking a table where we test the last row (index 1)
	    mockingContext.checking(new Expectations() {{
	        one(values).getColumnCount(); will(returnValue(2)); // Table has 2 columns
	        one(values).getValue(1, 0); will(returnValue(5.0)); // Row 1, Col 0 = 5.0
	        one(values).getValue(1, 1); will(returnValue(6.0)); // Row 1, Col 1 = 6.0
	    }});

	    // Calling the method on the last row
	    double result = DataUtilities.calculateRowTotal(values, 1);

	    // The expected sum is 5.0 + 6.0 = 11.0
	    assertEquals(11.0, result, 0.000000001);
	}


}
