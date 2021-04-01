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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.louisa.model.Invoice;
import com.louisa.model.User;

@Component
public class InvoiceService {

	UserService userService;
	String cdnUrl;
	JdbcTemplate jdbcTemplate;

	public InvoiceService(@Value("${cdn.url}") String cdnUrl, UserService userService, JdbcTemplate jdbcTemplate) {
		this.userService = userService;
		this.cdnUrl = cdnUrl;
		this.jdbcTemplate = jdbcTemplate;
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
	public List<Invoice> findAll() {
		 System.out.println("Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());
		return jdbcTemplate.query("select id, user_id, pdf_url, amount from invoices", 
		
		(resultSet, rowNum) -> {
		
			Invoice invoice = new Invoice();
			invoice.setId(resultSet.getObject("id").toString());
			invoice.setPdfUrl(resultSet.getString("pdf_url"));
			invoice.setUserId(resultSet.getString("user_id"));
			invoice.setAmount(resultSet.getInt("amount"));
			return invoice;
		});
	}

	@Transactional
	public Invoice create(String userId, Integer amount) {
		 System.out.println("Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());
		  String generatedPdfUrl = cdnUrl + "/images/default/sample.pdf";
		  KeyHolder keyHolder = new GeneratedKeyHolder();
		  jdbcTemplate.update(connection -> {
		        PreparedStatement ps = connection
		                .prepareStatement("insert into invoices (user_id, pdf_url, amount) values (?, ?, ?)",
		                        Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, userId);  // 
		        ps.setString(2, generatedPdfUrl);
		        ps.setInt(3, amount);
		        return ps;
		    }, keyHolder);
		   String uuid = !keyHolder.getKeys().isEmpty() ? ((UUID) keyHolder.getKeys().values().iterator().next()).toString()
		            : null;

		    Invoice invoice = new Invoice();
		    invoice.setId(uuid);
		    invoice.setPdfUrl(generatedPdfUrl);
		    invoice.setAmount(amount);
		    invoice.setUserId(userId);
		    return invoice;
	}
}