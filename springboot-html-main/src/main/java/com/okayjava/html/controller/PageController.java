package com.okayjava.html.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/login")
	public String log_in() {
		return "login";
	}

	@GetMapping("/customer")
	public String customer_data() {
		return "customer";
	}

	@GetMapping("/newcustomer")
	public String new_customer() {
		return "newcustomer";
	}
}
