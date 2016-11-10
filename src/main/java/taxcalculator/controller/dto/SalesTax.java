package taxcalculator.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import taxcalculator.controller.converter.TaxValueSerializer;

public class SalesTax {

	@JsonSerialize(using = TaxValueSerializer.class)
	private final double salesTax;

	public SalesTax(double salesTax) {
		this.salesTax = salesTax;
	}

	public double getSalesTax() {
		return salesTax;
	}
}