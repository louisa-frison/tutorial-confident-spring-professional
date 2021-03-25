package com.louisa.web;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louisa.app.context.Application;
import com.louisa.model.Invoice;
import com.louisa.service.InvoiceService;


public class MyFancyPDFInvoicesServlet  extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		if (req.getRequestURI().equalsIgnoreCase("/")) {
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().print("<html>\n" + "<body>\n" + "<h1>Hello World</h1>\n"
					+ "<p>This is my very first, embedded Tomcat, HTML Page!</p>\n" + "</body>\n" + "</html>");
		}else if (req.getRequestURI().equalsIgnoreCase("/invoices")) {
			resp.setContentType("application/json; charset=UTF-8");
			List<Invoice> invoices = Application.invoiceService.findAll(); 
			resp.getWriter().print(Application.objectMapper.writeValueAsString(invoices));
		}else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND); 
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	if (req.getRequestURI().equalsIgnoreCase("/invoices")) {
		String userId = req.getParameter("user_id"); 
		Integer amount = Integer.valueOf(req.getParameter("amount")); 
		Invoice invoice =  Application.invoiceService.create(userId, amount); 
		resp.setContentType("application/json; charset=UTF-8");
		String jsonString =  Application.objectMapper.writeValueAsString(invoice);
		resp.getWriter().print(jsonString);
	}else {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND); 
	}
	}
}
