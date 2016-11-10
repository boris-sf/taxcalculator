package taxcalculator;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Launcher.class)
public class ApplicationIntegrationTest {

	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = webAppContextSetup(ctx).build();
	}

	private ResultActions request(String requestJson) throws Exception {
		return mvc.perform(post("/taxcalculator").contentType(APPLICATION_JSON).content(requestJson));
	}

	@Test
	public void testSalesTaxCalculation_1() throws Exception {
		final String input = format("[%s]",
				arrayToCommaDelimitedString(
						new String[] { "{\"description\":\"book\", \"unitPrice\":12.49, \"count\":1}",
								"{\"description\":\"music CD\", \"unitPrice\":14.99, \"count\":1}",
								"{\"description\":\"chocolate bar\", \"unitPrice\":0.85, \"count\":1}" }));
		final MvcResult result = request(input).andExpect(status().isOk()).andReturn();
		assertEquals("{\"salesTax\":1.50}", result.getResponse().getContentAsString());
	}

	@Test
	public void testSalesTaxCalculation_2() throws Exception {
		final String input = format("[%s]",
				arrayToCommaDelimitedString(new String[] {
						"{\"description\":\"imported box of chocolates\", \"unitPrice\":10.0, \"count\":1}",
						"{\"description\":\"imported bottle of perfume\", \"unitPrice\":47.5, \"count\":1}" }));
		final MvcResult result = request(input).andExpect(status().isOk()).andReturn();
		assertEquals("{\"salesTax\":7.65}", result.getResponse().getContentAsString());
	}

	@Test
	public void testSalesTaxCalculation_3() throws Exception {
		final String input = format("[%s]",
				arrayToCommaDelimitedString(new String[] {
						"{\"description\":\"imported bottle of perfume\", \"unitPrice\":27.99, \"count\":1}",
						"{\"description\":\"bottle of perfume\", \"unitPrice\":18.99, \"count\":1}",
						"{\"description\":\"packet of headache pills\", \"unitPrice\":9.75, \"count\":1}",
						"{\"description\":\"box of imported chocolates\", \"unitPrice\":11.25, \"count\":1}" }));
		final MvcResult result = request(input).andExpect(status().isOk()).andReturn();
		assertEquals("{\"salesTax\":6.70}", result.getResponse().getContentAsString());
	}

	@Test
	public void testSalesTaxCalculation_4() throws Exception {
		final String input = "[{\"description\":\"box of imported chocolates\", \"unitPrice\":11.25, \"count\":2}]";
		final MvcResult result = request(input).andExpect(status().isOk()).andReturn();
		assertEquals("{\"salesTax\":1.15}", result.getResponse().getContentAsString());
	}

	@Test
	public void testSalesTaxCalculationForEmptyPurchaseList() throws Exception {
		final MvcResult result = request("[]").andExpect(status().isOk()).andReturn();
		assertEquals("{\"salesTax\":0.00}", result.getResponse().getContentAsString());
	}

	@Test
	public void testSalesTaxCalculationForUnspecifiedUnitPrice() throws Exception {
		final String input = "[{\"description\":\"book\",\"count\":1}]";
		final MvcResult result = request(input).andExpect(status().isOk()).andReturn();
		assertEquals("{\"salesTax\":0.00}", result.getResponse().getContentAsString());
	}

	@Test
	public void testSalesTaxCalculationForZeroUnitPrice() throws Exception {
		final String input = "[{\"description\":\"book\",\"unitPrice\":0, \"count\":1}]";
		final MvcResult result = request(input).andExpect(status().isOk()).andReturn();
		assertEquals("{\"salesTax\":0.00}", result.getResponse().getContentAsString());
	}

	@Test
	public void testEmptyPurchaseItemResultsInBadRequest() throws Exception {
		request("[{}]").andExpect(status().isBadRequest());
	}

	@Test
	public void testUnspecifiedDescriptionResultsInBadRequest() throws Exception {
		final String input = "[{\"unitPrice\":1, \"count\":1}]";
		request(input).andExpect(status().isBadRequest());
	}

	@Test
	public void testNullDescriptionResultsInBadRequest() throws Exception {
		final String input = "[{\"description\":null, \"unitPrice\":1, \"count\":1}]";
		request(input).andExpect(status().isBadRequest());
	}

	@Test
	public void testEmptyDescriptionResultsInBadRequest() throws Exception {
		final String input = "[{\"description\":\"\", \"unitPrice\":1, \"count\":1}]";
		request(input).andExpect(status().isBadRequest());
	}

	@Test
	public void testBlankDescriptionResultsInBadRequest() throws Exception {
		final String input = "[{\"description\":\"  \t  \n\", \"unitPrice\":1, \"count\":1}]";
		request(input).andExpect(status().isBadRequest());
	}

	@Test
	public void testNegativeUnitPriceResultsInBadRequest() throws Exception {
		final String input = "[{\"description\":\"1\", \"unitPrice\":-1, \"count\":1}]";
		request(input).andExpect(status().isBadRequest());
	}

	@Test
	public void testZeroCountResultsInBadRequest() throws Exception {
		final String input = "[{\"description\":\"1\", \"unitPrice\":10.7, \"count\":0}]";
		request(input).andExpect(status().isBadRequest());
	}

	@Test
	public void testNegativeCountResultsInBadRequest() throws Exception {
		final String input = "[{\"description\":\"1\", \"unitPrice\":10.7, \"count\":-2}]";
		request(input).andExpect(status().isBadRequest());
	}

	@Test
	public void testEmptyInputResultsInBadRequest() throws Exception {
		request("").andExpect(status().isBadRequest());
	}

	@Test
	public void testObjectInputResultsInBadRequest() throws Exception {
		request("{}").andExpect(status().isBadRequest());
	}

	@Test
	public void testNumberInputResultsInBadRequest() throws Exception {
		request("123").andExpect(status().isBadRequest());
	}

	@Test
	public void testStringInputResultsInBadRequest() throws Exception {
		request("\"message\"").andExpect(status().isBadRequest());
	}
}