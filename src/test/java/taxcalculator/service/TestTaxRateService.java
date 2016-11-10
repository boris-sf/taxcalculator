package taxcalculator.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static taxcalculator.service.TaxRateService.BASIC_TAX;
import static taxcalculator.service.TaxRateService.IMPORT_TAX;

import org.junit.Before;
import org.junit.Test;

import taxcalculator.model.Product;

public class TestTaxRateService {

	private static final double delta = 1e-15;

	private final Product mockProduct = mock(Product.class);
	private ProductService productService;
	private TaxRateService taxRateService;

	@Before
	public void setUp() {
		productService = mock(ProductService.class);
		taxRateService = new TaxRateService(productService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidInitilization() {
		new TaxRateService(null);
	}

	@Test
	public void testTaxRateForBasicTaxFreeProduct() {
		when(productService.isImported(any())).thenReturn(false);
		when(productService.isBasicTaxFree(any())).thenReturn(true);
		assertEquals(0, taxRateService.rate(mockProduct), delta);
	}

	@Test
	public void testTaxRateForBasicTaxFreeImportedProduct() {
		when(productService.isImported(any())).thenReturn(true);
		when(productService.isBasicTaxFree(any())).thenReturn(true);
		assertEquals(IMPORT_TAX, taxRateService.rate(mockProduct), delta);
	}

	@Test
	public void testTaxRateForRegularProduct() {
		when(productService.isImported(any())).thenReturn(false);
		when(productService.isBasicTaxFree(any())).thenReturn(false);
		assertEquals(BASIC_TAX, taxRateService.rate(mockProduct), delta);
	}

	@Test
	public void testTaxRateForRegularImportedProduct() {
		when(productService.isImported(any())).thenReturn(true);
		when(productService.isBasicTaxFree(any())).thenReturn(false);
		assertEquals(IMPORT_TAX + BASIC_TAX, taxRateService.rate(mockProduct), delta);
	}
}