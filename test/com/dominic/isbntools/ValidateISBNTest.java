package com.dominic.isbntools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidateISBNTest {

	@Test
	void checkAValidISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("1728235847");
		assertTrue(result, "First value");
		result = validator.checkISBN("1728235847");
		assertTrue(result, "Second value");
	}
	
	@Test
	void checkAnInvalidISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0492667838");
		assertFalse(result);
	}
}
