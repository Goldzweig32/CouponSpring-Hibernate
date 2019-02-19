package com.yaniv.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaniv.coupons.beans.Customer;
import com.yaniv.coupons.beans.CustomerEntity;
import com.yaniv.coupons.beans.UserId;
import com.yaniv.coupons.beans.UserLoginDetails;
import com.yaniv.coupons.controller.CustomerController;
import com.yaniv.coupons.exceptions.ApplicationException;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customers")
public class CustomerApi {

	@Autowired
	private CustomerController customerController;

	@PostMapping
	@RequestMapping("/register")
	public UserId registerCustomer(HttpServletRequest request,HttpServletResponse response, @RequestBody CustomerEntity customer)
			throws ApplicationException {
		// this.customerController.createCustomer(customer);
		this.customerController.registerCustomer(customer);
		long customerId = customer.getCustomerId();
		Cookie cookie = new Cookie("login", Long.toString(customerId));
		cookie.setPath("/");
		response.addCookie(cookie);
		UserId userId = new UserId(customerId);
		response.setStatus(200);
		request.getSession();
		return userId;
		
	}

	@DeleteMapping
	@RequestMapping("/{customerId}")
	public void deleteCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
		this.customerController.deleteCustomer(customerId);
	}

	@PutMapping
	@RequestMapping("/update")
	public void updateCustomer(@RequestBody CustomerEntity customer) throws ApplicationException {
		System.out.println(customer);
		this.customerController.updateCustomer(customer);
	}

	@GetMapping
	public List<CustomerEntity> getCustomers() throws ApplicationException {
		return this.customerController.getCustomers();
	}

	@GetMapping
	@RequestMapping("/showCustomer/{customerId}")
	public CustomerEntity getCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
		return this.customerController.getCustomerByCustomerId(customerId);
	}
}
