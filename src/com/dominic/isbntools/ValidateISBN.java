package com.dominic.isbntools;

public class ValidateISBN {

	public boolean checkISBN(String isbn) {
		int total = 0;
		
		if (isbn.length() == 13) {
			for (int i = 0; i < 13; i++) {
				if (i % 2 == 0) {
					total += Character.getNumericValue(isbn.charAt(i));
				} else {
					total += Character.getNumericValue(isbn.charAt(i)) * 3;
				}
			}
			
			return total % 10 == 0 ? true : false;
		} else {
			if (isbn.length() != 10)
				throw new NumberFormatException("ISBN numbers must be 10 digits long");

			for (int i = 0; i < 10; i++) {
				if (!Character.isDigit(isbn.charAt(i))) {
					if (i == 9 && isbn.charAt(i) == 'X') {
						total += 10;
					} else {
						throw new NumberFormatException("ISBN characters must be numbers");
					}
				} else {
					total += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
				}
			}

			return total % 11 == 0 ? true : false;
		}

	}
}
