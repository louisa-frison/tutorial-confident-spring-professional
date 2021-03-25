package com.louisa.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.louisa.app.context.Application;
import com.louisa.model.Invoice;
import com.louisa.model.User;

public class InvoiceService {
	UserService userService; 

	List<Invoice> invoices = new CopyOnWriteArrayList<>();
	
	public InvoiceService(UserService userService ) {
		this.userService = userService; 
	}
	public List<Invoice> findAll() {
		return invoices;
	}

	public Invoice create(String userId, Integer amount) {
		User user = userService.findById(userId);
		if (user == null) {
			throw new IllegalStateException();
		}
		Invoice invoice = new Invoice(userId, amount, "http://www.africau.edu/images/default/sample.pdf");
		invoices.add(invoice);
		return invoice;
	}

}