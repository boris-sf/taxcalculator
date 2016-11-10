package taxcalculator.controller.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import taxcalculator.controller.dto.Purchase;
import taxcalculator.model.Product;

public class TestPurchaseToProductConverter {

	private final Purchase purchase = new Purchase("book", 14.99, 3);

	@Test
	public void testDescriptionConversion() {
		final Product product = convert(purchase);
		assertEquals(purchase.getDescription(), product.getDescription());
	}

	@Test
	public void testTotalCostConversion() {
		final Product product = convert(purchase);
		final double totalCost = purchase.getUnitPrice() * purchase.getCount();
		assertEquals(totalCost, product.getTotalCost(), 1e-15);
	}

	private Product convert(Purchase value) {
		return new PurchaseToProductConverter().convert(value);
	}
}