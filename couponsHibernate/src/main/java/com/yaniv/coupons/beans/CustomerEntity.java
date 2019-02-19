package com.yaniv.coupons.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class CustomerEntity {
	
	@GeneratedValue
	@Id
	@Column(name="ID", nullable=false)
	private long customerId;
	
	@Column(name="CUSTOMER_NAME", nullable=false)
	private String customerName;
	
	@Column(name="PASSWORD", nullable=false)
	private String customerPassword;
	
	@Column(name="EMAIL", nullable=false)
	private String customerEmail;

	public CustomerEntity() {
		super();
	}

	public CustomerEntity(String customerName, String customerPassword, String customerEmail) {
		this.customerName = customerName;
		this.customerPassword = customerPassword;
		this.customerEmail = customerEmail;
	}

	public CustomerEntity(long customerId, String customerName, String customerPassword, String customerEmail) {
		this(customerName, customerPassword, customerEmail);
		this.customerId = customerId;

	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public String toString() {
		return "\n [Customer id= " + customerId + "Customer email= " + customerEmail + ", Customer name= "
				+ customerName + ", Customer password= " + customerPassword + "]";
	}
}
