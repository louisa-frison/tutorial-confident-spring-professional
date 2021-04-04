package com.louisa.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.louisa.model.Invoice;
import com.louisa.model.User;
import com.louisa.model.repository.InvoiceRepository;

@Component
public class InvoiceService {

	UserService userService;
	String cdnUrl;
	JdbcTemplate jdbcTemplate;
	InvoiceRepository invoiceRepository;

	public InvoiceService(@Value("${cdn.url}") String cdnUrl, UserService userService, JdbcTemplate jdbcTemplate,
			InvoiceRepository invoiceRepository) {
		this.userService = userService;
		this.cdnUrl = cdnUrl;
		this.jdbcTemplate = jdbcTemplate;
		this.invoiceRepository = invoiceRepository;
	}

	@PostConstruct
	public void init() {
		System.out.println("Fetching PDF Template from S3");
	}

	@PreDestroy
	private void shutdown() {
		System.out.println("Deleting downloaded temoplates ... ");

	}

	@Transactional
	public Iterable<Invoice> findAll() {
		return invoiceRepository.findAll();
	}

	@Transactional
	public Invoice create(String userId, Integer amount) {
		
		String generatedPdfUrl = cdnUrl + "/images/default/sample.pdf";
		Invoice invoice = new Invoice();
		invoice.setPdfUrl(generatedPdfUrl);
		invoice.setAmount(amount);
		invoice.setUserId(userId);
		return invoiceRepository.save(invoice);
	}

	public List<Invoice> findInvoiceByUserId(String userId) {
		return invoiceRepository.findByUserId(userId);
	}
}