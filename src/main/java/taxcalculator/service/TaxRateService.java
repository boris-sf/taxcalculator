package taxcalculator.service;

import static taxcalculator.utils.GeneralUtils.ensureNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import taxcalculator.model.Product;

@Component
public class TaxRateService {

	public static final int BASIC_TAX = 10;
	public static final int IMPORT_TAX = 5;

	private final ProductService productService;

	@Autowired
	public TaxRateService(ProductService productService) {
		ensureNotNull(productService, "ProductService");
		this.productService = productService;
	}

	public double rate(Product value) {
		double result = productService.isBasicTaxFree(value) ? 0 : BASIC_TAX;
		if (productService.isImported(value)) {
			result += IMPORT_TAX;
		}
		return result;
	}
}