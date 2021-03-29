package com.louisa.web;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louisa.app.context.MyFancyPdfInvoicesApplicationConfiguration;
import com.louisa.model.Invoice;
import com.louisa.service.InvoiceService;
import com.louisa.service.UserService;


public class MyFancyPDFInvoicesServlet  extends HttpServlet{
	
	private ObjectMapper objectMapper;
    private UserService userService; 
    private InvoiceService invoiceService;
    
	@Override
	public void init() throws ServletException {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(
				MyFancyPdfInvoicesApplicationConfiguration.class);
		acac.registerShutdownHook();
		objectMapper = acac.getBean(ObjectMapper.class);
		userService = acac.getBean(UserService.class);
		invoiceService = acac.getBean(InvoiceService.class);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		if (req.getRequestURI().equalsIgnoreCase("/")) {
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().print("<html>\n" + "<body>\n" + "<h1>Hello World</h1>\n"
					+ "<p>This is my very first, embedded Tomcat, HTML Page!</p>\n" + "</body>\n" + "</html>");
		}else if (req.getRequestURI().equalsIgnoreCase("/invoices")) {
			resp.setContentType("application/json; charset=UTF-8");
			List<Invoice> invoices = invoiceService.findAll(); 
			resp.getWriter().print(objectMapper.writeValueAsString(invoices));
		}else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND); 
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	if (req.getRequestURI().equalsIgnoreCase("/invoices")) {
		String userId = req.getParameter("user_id"); 
		Integer amount = Integer.valueOf(req.getParameter("amount")); 
		Invoice invoice =  invoiceService.create(userId, amount); 
		resp.setContentType("application/json; charset=UTF-8");
		String jsonString =  objectMapper.writeValueAsString(invoice);
		resp.getWriter().print(jsonString);
	}else {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND); 
	}
	}
}
