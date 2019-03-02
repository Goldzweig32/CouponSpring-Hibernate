package com.yaniv.coupons.beans;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yaniv.coupons.enums.CompanyStatus;

@Entity
@Table(name="company")
public class CompanyEntity {
	
	@GeneratedValue
	@Id
	@Column(name="ID", nullable=false)
	private long companyId;
	
	@Column(name="COMPANY_NAME", nullable=false)
	private String companyName;
	
	@Column(name="PASSWORD", nullable=false)
	private String companyPassword;
	
	@Column(name="EMAIL", nullable=false)
	private String companyEmail;
	
	@Column(name="COMPANY_STATUS", nullable=false)
	private CompanyStatus companyStatus;

	public CompanyEntity() {
		super();
	}

	public CompanyEntity(String companyName, String companyPassword, String companyEmail, CompanyStatus companyStatus) {
		super();
		this.companyName = companyName;
		this.companyPassword = companyPassword;
		this.companyEmail = companyEmail;
		this.companyStatus = companyStatus;
	}

	public CompanyEntity(long id, String companyName, String companyPassword, String companyEmail,
			CompanyStatus companyStatus) {
		super();
		this.companyId = id;
		this.companyName = companyName;
		this.companyPassword = companyPassword;
		this.companyEmail = companyEmail;
		this.companyStatus = companyStatus;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long id) {
		this.companyId = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPassword() {
		return companyPassword;
	}

	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyStatus() {
		return companyStatus.toString();
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = CompanyStatus.valueOf(companyStatus.toUpperCase());
	}

	@Override
	public String toString() {
		return "\n [Company id : " + companyId + ", Company name : " + companyName + ", Company password : "
				+ companyPassword + ", Company email : " + companyEmail + ",status" + companyStatus + "]";
	}
}
