package com.dominic.isbntools;

public class ValidateISBN {

	private static final int SHORT_ISBN_LENGTH = 10;
	private static final int LONG_ISBN_LENGTH = 13;

	public boolean checkISBN(String isbn) {
		int total = 0;
		
		if (isbn.length() == LONG_ISBN_LENGTH) {
			return isTHisAValid13DigitISBN(isbn, total);
		} else {
			if (isbn.length() != SHORT_ISBN_LENGTH)
				throw new NumberFormatException("ISBN numbers must be 10 digits long");

			return isThisAValid10DigitISBN(isbn, total);
		}
	}

	private boolean isThisAValid10DigitISBN(String isbn, int total) {
		for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {
			if (!Character.isDigit(isbn.charAt(i))) {
				if (i == 9 && isbn.charAt(i) == 'X') {
					total += 10;
				} else {
					throw new NumberFormatException("ISBN characters must be numbers");
				}
			} else {
				total += Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN_LENGTH - i);
			}
		}

		return total % 11 == 0 ? true : false;
	}

	private boolean isTHisAValid13DigitISBN(String isbn, int total) {
		for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
			if (i % 2 == 0) {
				total += Character.getNumericValue(isbn.charAt(i));
			} else {
				total += Character.getNumericValue(isbn.charAt(i)) * 3;
			}
		}
		
		return total % 10 == 0 ? true : false;
	}
}
