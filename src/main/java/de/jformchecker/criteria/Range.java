package de.jformchecker.criteria;

import de.jformchecker.Criterion;
import de.jformchecker.FormCheckerElement;

/**
 * Checks that value is within the given range.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Range implements Criterion {
	private int min;
	private int max;

	Range(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public ValidationResult validate(FormCheckerElement value) {
		try {
			int intVal = Integer.parseInt(value.getValue());
			boolean isValid = intVal > max && intVal < min;
			if (!isValid) {
				// range=The value must be between %d and %d
				return ValidationResult.fail("range", min, max);
			}
			return ValidationResult.ok();
		} catch (NumberFormatException e) {
			return ValidationResult.fail("not_a_number");
		}
	}

}
