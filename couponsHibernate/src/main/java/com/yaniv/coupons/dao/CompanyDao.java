package com.yaniv.coupons.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.yaniv.coupons.beans.CompanyEntity;
import com.yaniv.coupons.enums.CompanyStatus;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;


@Repository
public class CompanyDao {

	@PersistenceContext(unitName="couponHibernate")
	private EntityManager entityManager;

	
	@Transactional(propagation=Propagation.REQUIRED)
	public void registerCompany(CompanyEntity company) throws ApplicationException {

		try {
				company.setCompanyStatus("active");
				entityManager.persist(company);
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, creatCompany(); FAILED");
		}
	}

	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompany(long companyId) throws ApplicationException {


		try {
			return entityManager.find(CompanyEntity.class, companyId);

		}
		catch (NoResultException e) {
			throw new ApplicationException(ErrorType.COMPANY_DOES_NOT_EXIST, " No company with ID: " + companyId + "!");
		}
		catch (Exception e) {
			// logger.error("Error in CompanyDao, getCompany(); FAILED");
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, getCompany(); FAILED");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deactivateCompany(Long companyId) throws ApplicationException {

		try {
			
			String deactivate = "deactivate";
			Query query = entityManager.createNativeQuery("UPDATE company SET COMPANY_STATUS =:companyStatus WHERE ID =:companyId");
			query.setParameter("companyStatus", deactivate);
			query.setParameter("companyId", companyId);

			query.getSingleResult();			
		}

		catch (Exception e) {
			// logger.error("Error in CompanyDao, deactivateCompany(); FAILED");
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CompanyDao, deactivateCompany(); FAILED");
		}

	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCompany(CompanyEntity company) throws ApplicationException {

		try {
			entityManager.merge(company);
		}

		catch (Exception e) {
			// logger.error("Error in CompanyDao, updateCompany(); FAILED");
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, updateCompany(); FAILED");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CompanyEntity> getCompanies() throws ApplicationException {

		try {
			
			Query getCompaniesQuery = entityManager.createNativeQuery("SELECT * FROM company");
			return getCompaniesQuery.getResultList();
		
		} catch (NoResultException e) {
			throw new ApplicationException(e, ErrorType.THE_LIST_IS_EMPTY, "Error in CompanyDao, getCompanies(); FAILED There are no companies");
		} catch (Exception e) {
			// logger.error("Error in CompanyDao, getCompanies(); FAILED");
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, getCompanies(); FAILED");
		}
		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity checkLogin(String email, String password) throws ApplicationException {
		
		try {
			String status = "active";
			Query loginQuery = entityManager.createNativeQuery("SELECT ID FROM company WHERE EMAIL =:companyEmail  AND PASSWORD =:companyPassword AND COMPANY_STATUS =:companyStatus");
			loginQuery.setParameter("companyEmail", email);
			loginQuery.setParameter("companyPassword", password);
			loginQuery.setParameter("companyStatus", status);
			
			CompanyEntity company = (CompanyEntity) loginQuery.getSingleResult();
			return company;
		} 
		catch (NoResultException e) {
			return null;
		}
		catch (Exception e) {
			// logger.error("Error in CompanyDao, checkLogin(); FAILED");
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, checkLogin(); FAILED");
		}		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyExistByEmail(String companyEmail) throws ApplicationException {
		

		try {
			Query existQuery = entityManager.createNativeQuery("SELECT ID FROM company WHERE EMAIL =:companyEmail");
			existQuery.setParameter("companyEmail", companyEmail);
			CompanyEntity company = (CompanyEntity) existQuery.getSingleResult();
			if (company == null) {
				return false;
			}else return true;
				
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			// logger.error("Error in CompanyDao, isCompanyExistByEmail(); FAILED");
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CompanyDao, isCompanyExistByEmail(); FAILED");
		}
		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyExist(Long companyId) throws ApplicationException {
		
		try {
			Query existQuery = entityManager.createNativeQuery("SELECT ID FROM company WHERE ID =:companyId");
			existQuery.setParameter("companyId", companyId);
			CompanyEntity company = (CompanyEntity) existQuery.getSingleResult();
			if (company == null) {
				return false;
			}else return true;
				
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			// logger.error("Error in CompanyDao, isCompanyExistById(); FAILED");
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CompanyDao, isCompanyExistById(); FAILED");
		}
	}

}