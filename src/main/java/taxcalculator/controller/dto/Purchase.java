package taxcalculator.controller.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Purchase {

	@NotEmpty
	private final String description;
	private final double unitPrice;
	private final int count;

	@JsonCreator
	public Purchase(@JsonProperty("description") String description, @JsonProperty("unitPrice") double unitPrice,
			@JsonProperty("count") int count) {
		this.description = description;
		this.unitPrice = unitPrice;
		this.count = count;
	}

	public String getDescription() {
		return description;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getCount() {
		return count;
	}
}