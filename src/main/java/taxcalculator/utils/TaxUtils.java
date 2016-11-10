package taxcalculator.utils;

import java.text.DecimalFormat;

public final class TaxUtils {

	private static final DecimalFormat formatter = new DecimalFormat("0.00");

	public static String formatTaxValue(double value) {
		return formatter.format(value);
	}

	public static double calculateTaxAmount(double totalCost, double rate) {
		return 0.01 * rate * totalCost;
	}

	public static double roundTaxValue(double value) {
		return Math.ceil(value * 20) / 20;
	}
}