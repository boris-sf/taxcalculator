package taxcalculator.controller.converter;

import org.springframework.stereotype.Component;

import taxcalculator.controller.dto.Purchase;
import taxcalculator.model.Product;

@Component
public class PurchaseToProductConverter {

	public Product convert(Purchase value) {
		final double total = value.getUnitPrice() * value.getCount();
		return new Product(value.getDescription(), total);
	}
}