package com.louisa.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.louisa.model.Invoice;
import com.louisa.model.User;

@Component
public class InvoiceService {
	
	UserService userService; 
	String cdnUrl; 
	List<Invoice> invoices = new CopyOnWriteArrayList<>();
	
	public InvoiceService(@Value("${cdn.url}")String cdnUrl, UserService userService ) {
		this.userService = userService; 
		this.cdnUrl = cdnUrl; 
	}
	
	@PostConstruct
	public void init() {
System.out.println("Fetching PDF Template from S3");
	}
	
	@PreDestroy
	private void shutdown() {
	System.out.println("Deleting downloaded temoplates ... ");

	}
	public List<Invoice> findAll() {
		return invoices;
	}

	public Invoice create(String userId, Integer amount) {
		User user = userService.findById(userId);
		if (user == null) {
			throw new IllegalStateException();
		}
		Invoice invoice = new Invoice(userId, amount, cdnUrl + "/images/default/sample.pdf");
		invoices.add(invoice);
		return invoice;
	}

}