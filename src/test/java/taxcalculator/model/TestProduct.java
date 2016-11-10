package taxcalculator.model;

import org.junit.Test;

import taxcalculator.model.Product;

public class TestProduct {

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullDescription() {
		new Product(null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithEmptyDescription() {
		new Product("", 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithBlankDescription() {
		new Product("    ", 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNegativeCost() {
		new Product("description", -1e-15);
	}
}