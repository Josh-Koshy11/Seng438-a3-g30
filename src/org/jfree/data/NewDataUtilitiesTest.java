package org.jfree.data;

import static org.junit.Assert.*;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import java.util.Collections;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Arrays;

public class NewDataUtilitiesTest {

    private Mockery mockingContext = new Mockery();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Setup before any tests run.
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Cleanup after all tests run.
    }

    @Before
    public void setUp() throws Exception {
        // Setup before each test.
    }

    @After
    public void tearDown() throws Exception {
        // Cleanup after each test.
    }
    private static class DummyKeyedValues implements KeyedValues {
        private List<Comparable> keys;
        private List<Number> values;
        
        public DummyKeyedValues(List<Comparable> keys, List<Number> values) {
            this.keys = keys;
            this.values = values;
        }
        
        @Override
        public int getItemCount() {
            return keys.size();
        }
        
        @Override
        public Comparable getKey(int index) {
            return keys.get(index);
        }
        
        @Override
        public Number getValue(int index) {
            return values.get(index);
        }
        
        @Override
        public List getKeys() {
            return keys;
        }
        
        @Override
        public Number getValue(Comparable key) {
            int index = keys.indexOf(key);
            return (index >= 0) ? values.get(index) : null;
        }
        
        @Override
        public int getIndex(Comparable key) {
            return keys.indexOf(key);
        }
        
       
    }

    // A dummy implementation of KeyedValues that forces a negative item count.
    private static class DummyKeyedValuesNegative implements KeyedValues {
        // Return a negative count to force execution of the second loop.
        @Override
        public int getItemCount() {
            return -1;
        }
        
        @Override
        public Comparable getKey(int index) {
            if (index == 0) {
                return "key0";
            }
            throw new IndexOutOfBoundsException("dummy");
        }
        
        @Override
        public Number getValue(int index) {
            if (index == 0) {
                return 10.0;
            }
            throw new IndexOutOfBoundsException("dummy");
        }
        
        @Override
        public List getKeys() {
            return Collections.singletonList("key0");
        }
        
        @Override
        public Number getValue(Comparable key) {
            return 10.0;
        }
        
        @Override
        public int getIndex(Comparable key) {
            return 0;
        }
        
       
    }

    /**
     * Normal dataset test: keys: 0,1,2; values: 10.0, 20.0, 30.0.
     * Total = 60; running totals: 10, 30, 60.
     */
    @Test
    public void testGetCumulativePercentages_NormalDataset() {
        List<Comparable> keys = Arrays.asList(0, 1, 2);
        List<Number> values = Arrays.asList(10.0, 20.0, 30.0);
        DummyKeyedValues data = new DummyKeyedValues(keys, values);
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
        
        assertEquals(10.0 / 60.0, result.getValue(0).doubleValue(), 0.000001);
        assertEquals(30.0 / 60.0, result.getValue(1).doubleValue(), 0.000001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.000001);
    }

    /**
     * Test with zeros: keys: "A","B","C"; values: 0.0, 0.0, 10.0.
     * Total = 10; running totals: 0, 0, 10.
     */
    @Test
    public void testGetCumulativePercentages_DatasetWithZeros() {
        List<Comparable> keys = Arrays.asList("A", "B", "C");
        List<Number> values = Arrays.asList(0.0, 0.0, 10.0);
        DummyKeyedValues data = new DummyKeyedValues(keys, values);
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
        
        assertEquals(0.0, result.getValue(0).doubleValue(), 0.000001);
        assertEquals(0.0, result.getValue(1).doubleValue(), 0.000001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.000001);
    }

    /**
     * Test with negative values: keys: "X","Y","Z"; values: -5.0, 10.0, 15.0.
     * Total = 20; running totals: -5, 5, 20.
     */
    @Test
    public void testGetCumulativePercentages_DatasetWithNegativeValues() {
        List<Comparable> keys = Arrays.asList("X", "Y", "Z");
        List<Number> values = Arrays.asList(-5.0, 10.0, 15.0);
        DummyKeyedValues data = new DummyKeyedValues(keys, values);
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
        
        assertEquals(-5.0 / 20.0, result.getValue(0).doubleValue(), 0.000001);
        assertEquals(5.0 / 20.0, result.getValue(1).doubleValue(), 0.000001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.000001);
    }

    /**
     * Single value dataset: key: "OnlyKey"; value: 100.0.
     * Running percentage should be 1.0.
     */
    @Test
    public void testGetCumulativePercentages_SingleValueDataset() {
        List<Comparable> keys = Collections.singletonList("OnlyKey");
        List<Number> values = Collections.singletonList(100.0);
        DummyKeyedValues data = new DummyKeyedValues(keys, values);
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
        
        assertEquals(1.0, result.getValue(0).doubleValue(), 0.000001);
    }

    /**
     * Test with a null value: keys: "A","B","C"; values: null, 10.0, 20.0.
     * Total = 0 + 10 + 20 = 30; running totals: 0, 10, 30.
     */
    @Test
    public void testGetCumulativePercentages_WithNullValue() {
        List<Comparable> keys = Arrays.asList("A", "B", "C");
        List<Number> values = Arrays.asList(null, 10.0, 20.0);
        DummyKeyedValues data = new DummyKeyedValues(keys, values);
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
        
        assertEquals(0.0, result.getValue(0).doubleValue(), 0.000001);
        assertEquals(10.0 / 30.0, result.getValue(1).doubleValue(), 0.000001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.000001);
    }

    /**
     * Test that passing a null dataset triggers a null-check exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCumulativePercentages_NullDataset() {
        DataUtilities.getCumulativePercentages(null);
    }

    /**
     * Test to force execution of the second for-loop.
     * This dummy returns a negative item count so that the loop condition (i2 > getItemCount())
     * is true and the loop is entered. We then expect an IndexOutOfBoundsException
     * to be thrown to terminate the loop.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetCumulativePercentages_NegativeItemCount() {
        DummyKeyedValuesNegative data = new DummyKeyedValuesNegative();
        DataUtilities.getCumulativePercentages(data);
    }
    
    


    @Test
    public void testEqual_BothNull() {
        assertTrue(DataUtilities.equal(null, null));
    }

    @Test
    public void testEqual_OneNull() {
        double[][] arr = { { 1.0, 2.0 } };
        assertFalse(DataUtilities.equal(arr, null));
        assertFalse(DataUtilities.equal(null, arr));
    }

    @Test
    public void testEqual_DifferentLengths() {
        double[][] arr1 = { { 1.0, 2.0 }, { 3.0 } };
        double[][] arr2 = { { 1.0, 2.0 } };
        assertFalse(DataUtilities.equal(arr1, arr2));
    }

    @Test
    public void testEqual_DifferentValues() {
        double[][] arr1 = { { 1.0, 2.0 } };
        double[][] arr2 = { { 1.0, 3.0 } };
        assertFalse(DataUtilities.equal(arr1, arr2));
    }

    @Test
    public void testEqual_IdenticalValues() {
        double[][] arr1 = { { Double.NaN, Double.POSITIVE_INFINITY } };
        double[][] arr2 = { { Double.NaN, Double.POSITIVE_INFINITY } };
        assertTrue(DataUtilities.equal(arr1, arr2));
    }

    @Test
    public void testClone_DeepCopy() {
        double[][] original = { { 1.0, 2.0 }, { 3.0, 4.0 } };
        double[][] cloned = DataUtilities.clone(original);
        assertNotSame(original, cloned);
        assertTrue(DataUtilities.equal(original, cloned));
        // Change original and verify clone remains unchanged.
        original[0][0] = 10.0;
        assertFalse(DataUtilities.equal(original, cloned));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClone_NullInput() {
        DataUtilities.clone(null);
    }

   
    // DummyValues2D for normal scenarios.
    private static class DummyValues2D implements Values2D {
        private final Number[][] data;
        public DummyValues2D(Number[][] data) {
            this.data = data;
        }
        @Override
        public int getRowCount() {
            return data.length;
        }
        @Override
        public int getColumnCount() {
            return (data.length > 0) ? data[0].length : 0;
        }
        @Override
        public Number getValue(int row, int column) {
            return data[row][column];
        }
    }

    @Test
    public void testCalculateColumnTotal_Normal() {
        Number[][] table = { { 1.0, 2.0 }, { 3.0, null } };
        DummyValues2D values = new DummyValues2D(table);
        // For column 0: 1.0 + 3.0 = 4.0
        assertEquals(4.0, DataUtilities.calculateColumnTotal(values, 0), 0.0001);
        // For column 1: 2.0 + null = 2.0
        assertEquals(2.0, DataUtilities.calculateColumnTotal(values, 1), 0.0001);
    }

    @Test
    public void testCalculateColumnTotal_WithValidRows() {
        Number[][] table = { { 1.0, 2.0 }, { 3.0, 4.0 }, { 5.0, 6.0 } };
        DummyValues2D values = new DummyValues2D(table);
        // Only consider row 0 and row 2 for column 1: 2.0 + 6.0 = 8.0
        int[] validRows = { 0, 2 };
        assertEquals(8.0, DataUtilities.calculateColumnTotal(values, 1, validRows), 0.0001);
        // Test with an out-of-bound index (row 3 is ignored)
        int[] validRows2 = { 0, 3 };
        assertEquals(1.0, DataUtilities.calculateColumnTotal(values, 0, validRows2), 0.0001);
    }

    // Additional test to cover the second for-loop in calculateColumnTotal
    // We simulate a scenario where getRowCount() returns a negative number.
    private static class DummyValues2DNegativeRow implements Values2D {
        @Override
        public int getRowCount() {
            return -1; // Forces: for (int r2 = 0; r2 > -1; r2++) to execute.
        }
        @Override
        public int getColumnCount() {
            return 2;
        }
        @Override
        public Number getValue(int row, int column) {
            // To break out of the infinite loop, throw an exception after one iteration.
            if (row == 0) return 5.0;
            throw new IndexOutOfBoundsException("Dummy exception to break loop");
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCalculateColumnTotal_NegativeRowCount() {
        DummyValues2DNegativeRow dummy = new DummyValues2DNegativeRow();
        DataUtilities.calculateColumnTotal(dummy, 0);
    }

 
    @Test
    public void testCalculateRowTotal_Normal() {
        Number[][] table = { { 1.0, 2.0, null } };
        DummyValues2D values = new DummyValues2D(table);
        // Sum: 1.0 + 2.0 = 3.0
        assertEquals(3.0, DataUtilities.calculateRowTotal(values, 0), 0.0001);
    }

    @Test
    public void testCalculateRowTotal_WithValidCols() {
        Number[][] table = { { 1.0, 2.0, 3.0 } };
        DummyValues2D values = new DummyValues2D(table);
        // Consider columns 0 and 2: 1.0 + 3.0 = 4.0
        int[] validCols = { 0, 2 };
        assertEquals(4.0, DataUtilities.calculateRowTotal(values, 0, validCols), 0.0001);
        // Test with an out-of-bound index (column 3 is ignored)
        int[] validCols2 = { 1, 3 };
        assertEquals(2.0, DataUtilities.calculateRowTotal(values, 0, validCols2), 0.0001);
    }

    // Additional test to cover the second for-loop in calculateRowTotal.
    // We simulate a scenario where getColumnCount() returns a negative number.
    private static class DummyValues2DNegativeColumn implements Values2D {
        @Override
        public int getRowCount() {
            return 1;
        }
        @Override
        public int getColumnCount() {
            return -1;  // Forces: for (int c2 = 0; c2 > -1; c2++) to execute.
        }
        @Override
        public Number getValue(int row, int column) {
            if (column == 0) return 5.0;
            throw new IndexOutOfBoundsException("Dummy exception to break loop");
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCalculateRowTotal_NegativeColumnCount() {
        DummyValues2DNegativeColumn dummy = new DummyValues2DNegativeColumn();
        DataUtilities.calculateRowTotal(dummy, 0);
    }


    @Test
    public void testCreateNumberArray_Normal() {
        double[] input = { 1.0, 2.0, 3.0 };
        Number[] result = DataUtilities.createNumberArray(input);
        assertEquals(input.length, result.length);
        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i], result[i].doubleValue(), 0.0001);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray_NullInput() {
        DataUtilities.createNumberArray(null);
    }

    @Test
    public void testCreateNumberArray2D_Normal() {
        double[][] input = { { 1.0, 2.0 }, { 3.0, 4.0 } };
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertEquals(input.length, result.length);
        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i].length, result[i].length);
            for (int j = 0; j < input[i].length; j++) {
                assertEquals(input[i][j], result[i][j].doubleValue(), 0.0001);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray2D_NullInput() {
        DataUtilities.createNumberArray2D(null);
    }
}