package com.dominic.isbntools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidateISBNTest {

	@Test
	void checkAValid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("1728235847");
		assertTrue(result, "First value");
		result = validator.checkISBN("1728235847");
		assertTrue(result, "Second value");
	}
	
	@Test
	void checkAValid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9780120000302");
		assertTrue(result, "first value");
		result = validator.checkISBN("9781853267338");
		assertTrue(result, "second value");
	}
	
	@Test
	public void TenDigitISBNNumbersEndingInAnXAreValid() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("012000030X");
		assertTrue(result);
	}
	
	@Test
	void checkAnInvalid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0492667838");
		assertFalse(result);
	}
	
	@Test
	void checkAnInvalid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9780120000300");
		assertFalse(result);
	}
	
	@Test
	void nineDigitsdISBNsAreNotAllowed() { // we expect this test to throw an exception
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> { 
					validator.checkISBN("049266783");
				}
		);
	}
	
	@Test
	void nonNumericISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> { 
					validator.checkISBN("HelloWorld");
				}
		);
	}
}
