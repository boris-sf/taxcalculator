package taxcalculator.service;

import static taxcalculator.utils.GeneralUtils.ensureNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import taxcalculator.model.Product;

@Component
public class TaxRateService {

	public static final int BASIC_TAX = 10;
	public static final int IMPORT_TAX = 5;

	private final ProductService products;

	@Autowired
	public TaxRateService(ProductService products) {
		ensureNotNull(products, "ProductService");
		this.products = products;
	}

	public double rate(Product value) {
		double result = products.isBasicTaxFree(value) ? 0 : BASIC_TAX;
		if (products.isImported(value)) {
			result += IMPORT_TAX;
		}
		return result;
	}
}