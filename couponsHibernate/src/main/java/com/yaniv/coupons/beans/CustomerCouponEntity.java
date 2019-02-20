package com.yaniv.coupons.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="customer_coupon")
public class CustomerCouponEntity {

	@Column(name="CUSTOMER_ID", nullable=false)
	private long customerId;
	
	@Column(name="COUPON_ID", nullable=false)
	private long couponId;
}
