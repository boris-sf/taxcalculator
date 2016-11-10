package taxcalculator.model;

import static taxcalculator.utils.GeneralUtils.isBlank;

public class Product {

	private final String description;
	private final double totalCost;

	public Product(String description, double totalCost) {
		if (isBlank(description)) {
			throw new IllegalArgumentException("description must not be empty");
		}
		if (totalCost < 0) {
			throw new IllegalArgumentException("totalCost must be non-negative");
		}
		this.description = description;
		this.totalCost = totalCost;
	}

	public String getDescription() {
		return description;
	}

	public double getTotalCost() {
		return totalCost;
	}
}