package taxcalculator.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static taxcalculator.utils.TaxUtils.calculateTaxAmount;
import static taxcalculator.utils.TaxUtils.roundTaxValue;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import taxcalculator.model.Product;

public class TestTaxCalculationService {

	private static final double delta = 1e-15;

	private TaxCalculationService taxCalculationService;
	private TaxRateService taxRateService;

	@Before
	public void setUp() {
		taxRateService = mock(TaxRateService.class);
		taxCalculationService = new TaxCalculationService(taxRateService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidInitilization() {
		new TaxCalculationService(null);
	}

	@Test
	public void testTaxCalculationForEmptyProductList() {
		final List<Product> products = Collections.emptyList();
		assertEquals(0, taxCalculationService.calculateTax(products), delta);
	}

	@Test
	public void testTaxCalculationForZeroCostProduct() {
		final Product product = product(0);
		when(taxRateService.rate(product)).thenReturn(10d);
		assertEquals(0, taxCalculationService.calculateTax(asList(product)), delta);
	}

	@Test
	public void testTaxCalculationForTaxFreeProduct() {
		final Product product = product(12);
		when(taxRateService.rate(product)).thenReturn(0d);
		assertEquals(0, taxCalculationService.calculateTax(asList(product)), delta);
	}

	@Test
	public void testTaxCalculationForSingleProduct() {
		final double taxRate = 10;
		final Product product = product(14.99);
		when(taxRateService.rate(product)).thenReturn(taxRate);
		final double expected = roundTaxValue(calculateTaxAmount(product.getTotalCost(), taxRate));
		assertEquals(expected, taxCalculationService.calculateTax(asList(product)), delta);
	}

	@Test
	public void testTaxCalculationForSeveralProducts() {
		final double taxRate1 = 15;
		final Product product1 = product(27.99);
		when(taxRateService.rate(product1)).thenReturn(taxRate1);

		final double taxRate2 = 10;
		final Product product2 = product(18.99);
		when(taxRateService.rate(product2)).thenReturn(taxRate2);

		final double taxRate3 = 5;
		final Product product3 = product(11.25);
		when(taxRateService.rate(product3)).thenReturn(taxRate3);

		final double taxValue1 = calculateTaxAmount(product1.getTotalCost(), taxRate1);
		final double taxValue2 = calculateTaxAmount(product2.getTotalCost(), taxRate2);
		final double taxValue3 = calculateTaxAmount(product3.getTotalCost(), taxRate3);

		final double expected = roundTaxValue(taxValue1 + taxValue2 + taxValue3);
		assertEquals(expected, taxCalculationService.calculateTax(asList(product1, product2, product3)), delta);
	}

	private static Product product(double totalCost) {
		return new Product("book", totalCost);
	}
}