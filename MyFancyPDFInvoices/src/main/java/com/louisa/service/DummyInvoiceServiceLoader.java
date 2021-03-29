package com.louisa.service;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DummyInvoiceServiceLoader {

	InvoiceService invoiceService; 
	
	public DummyInvoiceServiceLoader(InvoiceService invoiceService) {
		this.invoiceService = invoiceService; 
	}
	
	@PostConstruct
	private void setup() {
		System.out.println("Creating dev invoices...");
		invoiceService.create("userId1", 50); 
		invoiceService.create("userId2", 99); 
	}
}
