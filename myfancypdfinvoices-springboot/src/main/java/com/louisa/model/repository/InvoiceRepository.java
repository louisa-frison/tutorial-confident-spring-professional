package com.louisa.model.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.louisa.model.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, String> {

	@Query("SELECT id, pdf_url, user_id, amount FROM \"invoices\" WHERE user_id = :userId")
	List<Invoice> findByUserId(@Param("userId") String userId);
}
