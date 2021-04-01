package com.louisa.web;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louisa.model.Invoice;
import com.louisa.model.dto.InvoiceDTO;
import com.louisa.service.InvoiceService;

@RestController
@Validated
public class InvoicesController {

	InvoiceService invoiceService;

	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@GetMapping(value ="/invoices", produces = "application/json")
	public List<Invoice> getAllInvoices() {
		return invoiceService.findAll();
	}

	@PostMapping("/invoices/{userId}/{amount}")
	public Invoice createInvoicePathVariable(@PathVariable @NotBlank String userId, @PathVariable @Max(50) @Min(10) Integer amount) {
		return invoiceService.create(userId, amount);
	}
	
	@PostMapping(value = "/invoices", consumes = "application/json")
	public Invoice createInvoiceJSONRequestBody(@RequestBody @Valid InvoiceDTO invoiceDTO) {
		return invoiceService.create(invoiceDTO.getUserId(), invoiceDTO.getAmount());
	}
	

}
