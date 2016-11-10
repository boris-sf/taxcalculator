package taxcalculator.controller.validation;

import javax.validation.ValidationException;

import org.junit.Test;

import taxcalculator.controller.dto.Purchase;

public class PurchaseValidatorTest {

	@Test(expected = ValidationException.class)
	public void testValidationOfNullDescription() {
		validate(new Purchase(null, 1, 1));
	}

	@Test(expected = ValidationException.class)
	public void testValidationOfEmptyDescription() {
		validate(new Purchase("", 1, 1));
	}

	@Test(expected = ValidationException.class)
	public void testValidationOfBlankDescription() {
		validate(new Purchase("\n\t ", 1, 1));
	}

	@Test(expected = ValidationException.class)
	public void testValidationOfNegativeUnitPrice() {
		validate(new Purchase("book", -1e-15, 1));
	}

	@Test
	public void testValidationOfZeroUnitPrice() {
		validate(new Purchase("book", 0.0, 1));
	}

	@Test
	public void testValidationOfPositiveUnitPrice() {
		validate(new Purchase("book", 1e-15, 1));
	}

	@Test(expected = ValidationException.class)
	public void testValidationOfNegativeCount() {
		validate(new Purchase("book", 3, -1));
	}

	@Test(expected = ValidationException.class)
	public void testValidationOfZeroCount() {
		validate(new Purchase("book", 3, 0));
	}

	@Test
	public void testValidationOfPositiveCount() {
		validate(new Purchase("book", 3, 1));
	}

	private void validate(Purchase value) {
		new PurchaseValidator().validate(value);
	}
}