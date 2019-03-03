package com.yaniv.coupons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import com.yaniv.coupons.beans.CompanyEntity;
import com.yaniv.coupons.beans.UserLoginDetails;
import com.yaniv.coupons.dao.CompanyDao;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.DateUtils;
import com.yaniv.coupons.utils.ValidationUtils;

@Controller
public class CompanyController {

	@Autowired
	private CompanyDao companyDao;

	// public CompanyController(){
	// this.companyDao = new CompanyDao();
	// }

	public void registerCompany(CompanyEntity company) throws ApplicationException {
		// We validate the creation of a new coupon
		//validateRegisterCompany(userLoginDetails);

		// If we didn't catch any exception, we call the 'createCoupon' method.
		this.companyDao.registerCompany(company);

	}


	public CompanyEntity getCompany(Long companyId) throws ApplicationException {

		if (!companyDao.isCompanyExist(companyId)) {
			throw new ApplicationException(ErrorType.COMPANY_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + " Get company by company id has failed."
							+ "\nThe user attempted to get company by id which does not exist." + "\nCompany Id ="
							+ companyId);
		}

		return companyDao.getCompany(companyId);
	}

	public void deactivateCompany(Long companyId) throws ApplicationException {

		if (!companyDao.isCompanyExist(companyId)) {
			throw new ApplicationException(ErrorType.COMPANY_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + " Delete company has failed."
							+ "\nThe user attempted to delete company by id which does not exist." + "\nCompany Id ="
							+ companyId);
		}

		companyDao.deactivateCompany(companyId);
	}

	public void updateCompany(CompanyEntity company) throws ApplicationException {

		if (!companyDao.isCompanyExist(company.getCompanyId())) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + " Update company has failed."
							+ "\nThe user attempted to update company by id which does not exist." + "\nCompany Id ="
							+ company.getCompanyId());
		}
		companyDao.updateCompany(company);
	}

	public List<CompanyEntity> getCompanies() throws ApplicationException {
		List<CompanyEntity> companies = companyDao.getCompanies();
		return companies;
	}

	public CompanyEntity checkLogin(String email, String password) throws ApplicationException {
		CompanyEntity company = companyDao.checkLogin(email, password);
		if (company == null) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL_OR_PASSWORD, DateUtils.getCurrentDateAndTime()
					+ " Check login has failed." + "\nThe user attempted to login with invalid email or password.");
		}
		return company;
	}

	private void validateRegisterCompany(UserLoginDetails userLoginDetails) throws ApplicationException {

		// We check if the email is valid
		if (!ValidationUtils.isEmailValid(userLoginDetails.getUserEmail())) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL_OR_PASSWORD,
					DateUtils.getCurrentDateAndTime() + " Create company has failed."
							+ "\nThe user attempted to create a new company using an invaild email or password.");
		}

		// We check if the coupon's name is already exist in the DB
		if (this.companyDao.isCompanyExistByEmail(userLoginDetails.getUserEmail())) {
			throw new ApplicationException(ErrorType.EMAIL_IS_ALREADY_EXISTS,
					DateUtils.getCurrentDateAndTime() + " Create company has failed."
							+ "\nThe user attempted to create a new company using an email that is already exists."
							+ "\nCompany Email =" + userLoginDetails.getUserEmail());
		}

		// We check if the password is valid
		if (!ValidationUtils.isPasswordValid(userLoginDetails.getUserPassword())) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL_OR_PASSWORD,
					DateUtils.getCurrentDateAndTime() + " Create company has failed."
							+ "\nThe user attempted to create a new company using an invaild email or password.");
		}
	}
}
