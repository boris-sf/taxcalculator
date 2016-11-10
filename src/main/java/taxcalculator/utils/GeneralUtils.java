package taxcalculator.utils;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public final class GeneralUtils {

	public static boolean hasOccurencesIgnoreCase(String value, String regexp) {
		final String pattern = format("(.*\\s|)(%s)(\\s.*|)", valueOf(regexp));
		return valueOf(value).toLowerCase().matches(pattern.toLowerCase());
	}

	public static <T> void ensureNotNull(T value, String name) {
		if (value == null) {
			throw new IllegalArgumentException(format("%s must not be null", name));
		}
	}

	public static boolean isBlank(String value) {
		return value == null || value.isEmpty() || value.matches("\\s*");
	}

	public static Throwable rootCause(Throwable ex) {
		for (Throwable t = ex; t != null; t = t.getCause()) {
			if (t.getCause() == null) {
				return t;
			}
		}
		return null;
	}
}