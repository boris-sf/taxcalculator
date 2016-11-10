package taxcalculator.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import taxcalculator.model.Product;

public class TestProductService {

	@Test
	public void testBookProductDetection() {
		final ProductService service = new ProductService();
		assertTrue(service.isBasicTaxFree(product("java book")));
	}

	@Test
	public void testMedicalProductDetection() {
		final ProductService service = new ProductService();
		assertTrue(service.isBasicTaxFree(product("headache pills")));
	}

	@Test
	public void testFoodProductDetection() {
		final ProductService service = new ProductService();
		assertTrue(service.isBasicTaxFree(product("chocolate bar")));
	}

	@Test
	public void testNegativeBasicTaxFreeProductDetection() {
		final ProductService service = new ProductService();
		assertFalse(service.isBasicTaxFree(product("BMW tyres")));
	}

	@Test
	public void testImportedProductDetection() {
		final ProductService service = new ProductService();
		assertTrue(service.isImported(product("box of imported jars")));
	}

	@Test
	public void testImportedProductDetectionNegative() {
		final ProductService service = new ProductService();
		assertFalse(service.isImported(product("java book")));
		assertFalse(service.isImported(product("headache pills")));
		assertFalse(service.isImported(product("chocolate bar")));
		assertFalse(service.isImported(product("chocolate in pills")));
	}

	private static Product product(String description) {
		final Product product = mock(Product.class);
		when(product.getDescription()).thenReturn(description);
		return product;
	}
}