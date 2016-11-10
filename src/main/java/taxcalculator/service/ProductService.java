package taxcalculator.service;

import static taxcalculator.utils.GeneralUtils.hasOccurencesIgnoreCase;

import org.springframework.stereotype.Component;

import taxcalculator.model.Product;

@Component
public class ProductService {

	/**
	 * Assume that product is free of basic tax in case when it's description
	 * contains one of some predefined words (ignoring case)
	 */
	public boolean isBasicTaxFree(Product value) {
		return hasOccurencesIgnoreCase(value.getDescription(), "book(s|)|pill(s|)|chocolate(s|)");
	}

	/**
	 * Assume that product is imported if it's description contains 'imported'
	 * word (ignoring case)
	 */
	public boolean isImported(Product value) {
		return hasOccurencesIgnoreCase(value.getDescription(), "imported");
	}
}