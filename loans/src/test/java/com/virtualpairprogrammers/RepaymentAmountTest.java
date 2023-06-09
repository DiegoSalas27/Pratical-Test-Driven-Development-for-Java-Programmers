package com.virtualpairprogrammers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Spy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

class RepaymentAmountTest {
	
	@Spy
	LoanApplication loanApplication;
	
	LoanCalculatorController controller;
	
	@BeforeEach
	public void setup() {
		loanApplication = spy(new LoanApplication());
		controller = new LoanCalculatorController();
		
		// fakes or dummies
		LoanRepository repository = mock(LoanRepository.class);
		JavaMailSender mailSender = mock(JavaMailSender.class);
		RestTemplate restTemplate = mock(RestTemplate.class);
		
		controller.setData(repository);
		controller.setMailSender(mailSender);
		controller.setRestTemplate(restTemplate);
	}
	
	@Test
	void test1YearLoanWholePounds() {
		loanApplication.setPrincipal(1200);
		loanApplication.setTermInMonths(12);
		
		doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(110), loanApplication.getRepayment());
	}
	
	@Test
	void test2YearLoanWholePounds() {
		loanApplication.setPrincipal(1200);
		loanApplication.setTermInMonths(24);
		
		doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(60), loanApplication.getRepayment());
	}
	
	@Test
	void test5YearLoanWholePounds() {
		loanApplication.setPrincipal(5000);
		loanApplication.setTermInMonths(60);
		
		doReturn(new BigDecimal(6.5)).when(loanApplication).getInterestRate();
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(111), loanApplication.getRepayment());
	}
}	
