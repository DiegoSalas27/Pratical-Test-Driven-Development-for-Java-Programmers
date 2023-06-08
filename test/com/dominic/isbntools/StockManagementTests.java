package com.dominic.isbntools;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class StockManagementTests {

	@Test
	void testCanGetACorrectLocatorCode() {		
		ExternalISBNDataService testWebService = mock(ExternalISBNDataService.class);
		when(testWebService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Steinbeck"));
		
		ExternalISBNDataService testDatabaseService = mock(ExternalISBNDataService.class);
		when(testDatabaseService.lookup(anyString())).thenReturn(null);
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		stockManager.setDatabaseService(testDatabaseService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode); // expectedValue, actualValue
	}
	
	@Test
	public void databaseIsUsedIfDataIsPresent() {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		// mock an implementation
		
		when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		
		// verify that lookup method in databaseService has been called once
		
		// verify(databaseService, times(1)).lookup("0140177396");
		verify(databaseService).lookup("0140177396");
		verify(webService, never()).lookup(anyString());
	}
	
	@Test
	public void webServiceIsUSedIfDataIsNotPresentInDatabase() {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		// mock an implementation
		
		when(databaseService.lookup("0140177396")).thenReturn(null);
		when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		
		// verify that lookup method in databaseService has been called once
		
		verify(databaseService, times(1)).lookup("0140177396");
		verify(webService, times(1)).lookup("0140177396");
	}
}
