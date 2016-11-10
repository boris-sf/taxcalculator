package taxcalculator.utils;

import static org.junit.Assert.assertEquals;
import static taxcalculator.utils.TaxUtils.calculateTaxAmount;
import static taxcalculator.utils.TaxUtils.formatTaxValue;
import static taxcalculator.utils.TaxUtils.roundTaxValue;

import org.junit.Test;

public class TestTaxUtils {

	private static final double delta = 1e-15;

	@Test
	public void testZeroValueFormatting() {
		assertEquals("0.00", formatTaxValue(0));
	}

	@Test
	public void testOneDecimalSignValueFormatting() {
		assertEquals("6.70", formatTaxValue(6.7));
	}

	@Test
	public void testTwoDecimalSignValueFormatting() {
		assertEquals("123.45", formatTaxValue(123.45));
	}

	@Test
	public void testCalculation() {
		assertEquals(1, calculateTaxAmount(20, 5), delta);
	}

	@Test
	public void testTaxAmountCalculationWithZeroRateAndCost() {
		assertEquals(0, calculateTaxAmount(0, 0), delta);
	}

	@Test
	public void testTaxAmountCalculationWithZeroRate() {
		assertEquals(0, calculateTaxAmount(10, 0), delta);
	}

	@Test
	public void testTaxAmountCalculationWithZeroCost() {
		assertEquals(0, calculateTaxAmount(0, 10), delta);
	}

	@Test
	public void testRoundZeroValue() {
		assertEquals(0, roundTaxValue(0), delta);
	}

	@Test
	public void testRoundIntegerValue() {
		assertEquals(4, roundTaxValue(4), delta);
	}

	@Test
	public void testRoundOneDecimalSignValue() {
		assertEquals(2.3, roundTaxValue(2.3), delta);
	}

	@Test
	public void testRoundTwoDecimalSignValue() {
		assertEquals(6.7, roundTaxValue(6.67), delta);
	}

	@Test
	public void testRoundThreeDecimalSignValue() {
		assertEquals(2.15, roundTaxValue(2.141), delta);
	}
}