package taxcalculator.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static taxcalculator.utils.GeneralUtils.hasOccurencesIgnoreCase;
import static taxcalculator.utils.GeneralUtils.isBlank;

import org.junit.Test;

public class TestGeneralUtils {

	private static final String line = "Line of SOme text aBout impoRtEd books medicine PILl chocolates and CHOcolate bars";

	@Test
	public void testAnyOfWordsOccurenceDetection() {
		assertTrue(hasOccurencesIgnoreCase(line, "about|pill"));
	}

	@Test
	public void testWordVariantsOccurenceDetection() {
		assertTrue(hasOccurencesIgnoreCase(line, "book(s|)"));
	}

	@Test
	public void testAnyOfWordVariantsOccurenceDetection() {
		assertTrue(hasOccurencesIgnoreCase(line, "book(s|)|chocolate(s|)|pill(s|)"));
	}

	@Test
	public void testSingeWordOccurenceDetection() {
		assertTrue(hasOccurencesIgnoreCase(line, "chocolates"));
	}

	@Test
	public void testNullStringRecognizedAsBlank() {
		assertTrue(isBlank(null));
	}

	@Test
	public void testEmptyStringRecognizedAsBlank() {
		assertTrue(isBlank(""));
	}

	@Test
	public void testStringOfWhitespacesRecognizedAsBlank() {
		assertTrue(isBlank("    "));
	}

	@Test
	public void testStringOfNewlineRecognizedAsBlank() {
		assertTrue(isBlank("\n\n"));
	}

	@Test
	public void testStringOfTabRecognizedAsBlank() {
		assertTrue(isBlank("\t"));
	}

	@Test
	public void testStringWithoutWhitespacesIsNotBlack() {
		assertFalse(isBlank("someline"));
	}

	@Test
	public void testStringWithAlphaAndWhitespacesIsNotBlack() {
		assertFalse(isBlank(" some\t text\ngoes here"));
	}
}