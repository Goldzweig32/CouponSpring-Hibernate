package com.yaniv.coupons.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="customer_coupon")
public class CustomerCouponEntity {

	
	@Id
	@GeneratedValue
	@Column(name="ID", nullable=false)
	private long id; 
	
	@Column(name="CUSTOMER_ID", nullable=false)
	private long customerId;
	
	@Column(name="COUPON_ID", nullable=false)
	private long couponId;
	
	@ManyToOne
	private CustomerEntity customer;
	
	@ManyToOne
	private CouponEntity coupon;
	
	public CouponEntity getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponEntity coupon) {
		this.coupon = coupon;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
	
	
}
