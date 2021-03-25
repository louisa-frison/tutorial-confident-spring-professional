package com.louisa.app.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louisa.service.InvoiceService;
import com.louisa.service.UserService;

public class Application {
	    public static final ObjectMapper objectMapper = new ObjectMapper();
	    public static final UserService userService = new UserService(); 
	    public static final InvoiceService invoiceService = new InvoiceService(userService);
}
