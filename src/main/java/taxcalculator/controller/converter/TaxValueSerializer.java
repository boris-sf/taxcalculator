package taxcalculator.controller.converter;

import static taxcalculator.utils.TaxUtils.formatTaxValue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TaxValueSerializer extends JsonSerializer<Number> {

	@Override
	public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeNumber(formatTaxValue(value.doubleValue()));
	}
}