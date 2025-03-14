package org.jfree.data;

import static org.junit.Assert.*;
import org.jmock.Expectations;
import org.jmock.Mockery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataUtilitiesTest {

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

	 private Mockery mockingContext = new Mockery();

	 @Test
	 public void testGetCumulativePercentages_NormalDataset() {
	     // Test: Calculate cumulative percentages for a typical dataset
	     final KeyedValues data = mockingContext.mock(KeyedValues.class);

	     mockingContext.checking(new Expectations() {{
	         allowing(data).getItemCount(); will(returnValue(3));

	         // Mock keys for the dataset
	         allowing(data).getKey(0); will(returnValue(0));
	         allowing(data).getKey(1); will(returnValue(1));
	         allowing(data).getKey(2); will(returnValue(2));

	         // Mock values corresponding to keys
	         allowing(data).getValue(0); will(returnValue(10.0));
	         allowing(data).getValue(1); will(returnValue(20.0));
	         allowing(data).getValue(2); will(returnValue(30.0));
	     }});

	     // Call the method to get cumulative percentages
	     KeyedValues result = DataUtilities.getCumulativePercentages(data);

	     // Check expected cumulative percentages
	     assertEquals(1.0 / 6.0, result.getValue(0).doubleValue(), 0.0000001);
	     assertEquals(3.0 / 6.0, result.getValue(1).doubleValue(), 0.0000001);
	     assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);
	 }

	 @Test
	 public void testGetCumulativePercentages_DatasetWithZeros() {
	     // Test: A dataset where some values are zero
	     final KeyedValues data = mockingContext.mock(KeyedValues.class);

	     mockingContext.checking(new Expectations() {{
	         allowing(data).getItemCount(); will(returnValue(3));

	         // Mock keys
	         allowing(data).getKey(0); will(returnValue(0));
	         allowing(data).getKey(1); will(returnValue(1));
	         allowing(data).getKey(2); will(returnValue(2));

	         // Values with zeros
	         allowing(data).getValue(0); will(returnValue(0.0));
	         allowing(data).getValue(1); will(returnValue(0.0));
	         allowing(data).getValue(2); will(returnValue(10.0));
	     }});

	     // Call method
	     KeyedValues result = DataUtilities.getCumulativePercentages(data);

	     // Check expected cumulative percentages
	     assertEquals(0.0, result.getValue(0).doubleValue(), 0.0000001);
	     assertEquals(0.0, result.getValue(1).doubleValue(), 0.0000001);
	     assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);
	 }

	 @Test
	 public void testGetCumulativePercentages_DatasetWithNegativeValues() {
	     // Test: A dataset with negative numbers
	     final KeyedValues data = mockingContext.mock(KeyedValues.class);

	     mockingContext.checking(new Expectations() {{
	         allowing(data).getItemCount(); will(returnValue(3));

	         // Mock keys
	         allowing(data).getKey(0); will(returnValue(0));
	         allowing(data).getKey(1); will(returnValue(1));
	         allowing(data).getKey(2); will(returnValue(2));

	         // Negative and positive values
	         allowing(data).getValue(0); will(returnValue(-5.0));
	         allowing(data).getValue(1); will(returnValue(10.0));
	         allowing(data).getValue(2); will(returnValue(15.0));
	     }});

	     // Call method
	     KeyedValues result = DataUtilities.getCumulativePercentages(data);

	     // Check expected cumulative percentages
	     assertEquals(-5.0 / 20.0, result.getValue(0).doubleValue(), 0.0000001);
	     assertEquals(5.0 / 20.0, result.getValue(1).doubleValue(), 0.0000001);
	     assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);
	 }

	 @Test
	 public void testGetCumulativePercentages_SingleValueDataset() {
	     // Test: A dataset with only one value
	     final KeyedValues data = mockingContext.mock(KeyedValues.class);

	     mockingContext.checking(new Expectations() {{
	         allowing(data).getItemCount(); will(returnValue(1));

	         // Only one key-value pair
	         allowing(data).getKey(0); will(returnValue(0));
	         allowing(data).getValue(0); will(returnValue(100.0));
	     }});

	     // Call method
	     KeyedValues result = DataUtilities.getCumulativePercentages(data);

	     // Since there's only one value, it should be 100% of the total
	     assertEquals(1.0, result.getValue(0).doubleValue(), 0.0000001);
	 }

	 @Test(expected = IllegalArgumentException.class)
	 public void testGetCumulativePercentages_NullDataset() {
	     // Test: Passing null should throw an IllegalArgumentException
	     DataUtilities.getCumulativePercentages(null);
	 }


}
