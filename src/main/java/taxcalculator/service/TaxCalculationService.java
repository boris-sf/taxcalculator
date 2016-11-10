package taxcalculator.service;

import static taxcalculator.utils.GeneralUtils.ensureNotNull;
import static taxcalculator.utils.TaxUtils.calculateTaxAmount;
import static taxcalculator.utils.TaxUtils.roundTaxValue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import taxcalculator.model.Product;

@Component
public class TaxCalculationService {

	private final TaxRateService taxRateService;

	@Autowired
	public TaxCalculationService(TaxRateService taxRateService) {
		ensureNotNull(taxRateService, "TaxRateService");
		this.taxRateService = taxRateService;
	}

	public double calculateTax(Collection<Product> items) {
		double total = 0;
		for (Product item : items) {
			total += calculateTaxAmount(item.getTotalCost(), taxRateService.rate(item));
		}
		return roundTaxValue(total);
	}
}