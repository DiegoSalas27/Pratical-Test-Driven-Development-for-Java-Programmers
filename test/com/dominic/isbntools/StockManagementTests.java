package com.dominic.isbntools;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockManagementTests {
	ExternalISBNDataService testWebService;
	ExternalISBNDataService testDatabaseService;
	StockManager stockManager;
	
	@BeforeEach
	public void setup() {
		testWebService = mock(ExternalISBNDataService.class);
		testDatabaseService = mock(ExternalISBNDataService.class);
		stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		stockManager.setDatabaseService(testDatabaseService);
	}

	@Test
	void testCanGetACorrectLocatorCode() {		
		when(testWebService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Steinbeck"));
		when(testDatabaseService.lookup(anyString())).thenReturn(null);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode); // expectedValue, actualValue
	}
	
	@Test
	public void databaseIsUsedIfDataIsPresent() {		
		// mock an implementation
		when(testDatabaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		String isbn = "0140177396";
		stockManager.getLocatorCode(isbn);
		
		// verify that lookup method in databaseService has been called once
		
		// verify(databaseService, times(1)).lookup("0140177396");
		verify(testDatabaseService).lookup("0140177396");
		verify(testWebService, never()).lookup(anyString());
	}
	
	@Test
	public void webServiceIsUSedIfDataIsNotPresentInDatabase() {		
		// mock an implementation
		
		when(testDatabaseService.lookup("0140177396")).thenReturn(null);
		when(testWebService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		String isbn = "0140177396";
		stockManager.getLocatorCode(isbn);
		
		// verify that lookup method in databaseService has been called once
		
		verify(testDatabaseService, times(1)).lookup("0140177396");
		verify(testWebService, times(1)).lookup("0140177396");
	}
}
