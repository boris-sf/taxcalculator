package taxcalculator.controller;

import static taxcalculator.utils.GeneralUtils.rootCause;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import taxcalculator.controller.converter.PurchaseToProductConverter;
import taxcalculator.controller.dto.Purchase;
import taxcalculator.controller.dto.SalesTax;
import taxcalculator.controller.validation.PurchaseValidator;
import taxcalculator.model.Product;
import taxcalculator.service.TaxCalculationService;

@RestController
public class TaxCalculationController {

	@Autowired
	private PurchaseValidator validator;

	@Autowired
	private TaxCalculationService taxCalculator;

	@Autowired
	private PurchaseToProductConverter productConverter;

	@RequestMapping(value = "/taxcalculator", method = RequestMethod.POST)
	public SalesTax calculate(@RequestBody List<Purchase> items) {
		items.forEach(validator::validate);
		final List<Product> products = items.stream().map(productConverter::convert).collect(Collectors.toList());
		return new SalesTax(taxCalculator.calculateTax(products));
	}

	@ExceptionHandler
	void handle(Exception ex, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), rootCause(ex).getMessage());
	}
}