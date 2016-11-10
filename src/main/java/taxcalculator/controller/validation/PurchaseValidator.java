package taxcalculator.controller.validation;

import static taxcalculator.utils.GeneralUtils.isBlank;

import javax.validation.ValidationException;

import org.springframework.stereotype.Component;

import taxcalculator.controller.dto.Purchase;

@Component
public class PurchaseValidator {

	public void validate(Purchase value) {
		validateDescriptionsNotBlank(value);
		validatePriceUnitNonNegative(value);
		validateCountPositive(value);
	}

	private void validateDescriptionsNotBlank(Purchase value) {
		if (isBlank(value.getDescription())) {
			throw new ValidationException("description must be non-empty");
		}
	}

	private void validatePriceUnitNonNegative(Purchase value) {
		if (value.getUnitPrice() < 0) {
			throw new ValidationException("unitPrice must not be non-negative");
		}
	}

	private void validateCountPositive(Purchase value) {
		if (value.getCount() <= 0) {
			throw new ValidationException("count must be positive");
		}
	}
}