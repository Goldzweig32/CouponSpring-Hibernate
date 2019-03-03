package com.yaniv.coupons.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yaniv.coupons.beans.CustomerEntity;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;

@Repository
public class CustomerDao {

	@PersistenceContext(unitName="couponHibernate")
	private EntityManager entityManager;

	@Transactional(propagation=Propagation.REQUIRED)
	public void registerCustomer(CustomerEntity customer) throws ApplicationException {


		try {

			entityManager.persist(customer);
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, createCustomer(); FAILED");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public CustomerEntity getCustomer(long customerId) throws ApplicationException {

		try {
			return entityManager.find(CustomerEntity.class,customerId);

		} catch (NoResultException e) {
			throw new ApplicationException(e, ErrorType.CUSTOMER_DOES_NOT_EXIST,
					"Error in CustomerDao, getCustomerByCustomerId(); FAILED to return a customer with id:" + customerId +".");
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CustomerDao, getCustomerByCustomerId(); FAILED");
		}
	}


	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCustomer(Long customerId) throws ApplicationException {

		CustomerEntity customer = getCustomer(customerId);
		try {

			entityManager.remove(customer);


		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, deleteCustomer(); FAILED");
		}


	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCustomer(CustomerEntity customer) throws ApplicationException {

		try {
			entityManager.merge(customer);
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, updateCustomer(); FAILED");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CustomerEntity> getCustomers() throws ApplicationException {

		try {

			Query getCustomersQuery = entityManager.createQuery("SELECT * FROM customer");
			return getCustomersQuery.getResultList();

		} catch (NoResultException e) {
			throw new ApplicationException(e, ErrorType.THE_LIST_IS_EMPTY, "Error in CustomerDao, getAllCustomer(); FAILED, THE LIST IS EMPTY!");
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, getAllCustomer(); FAILED");
		}

	}

	@Transactional(propagation=Propagation.REQUIRED)
	public CustomerEntity checkLogin(String customerEmail, String password) throws ApplicationException {



		try {
			Query loginQuery = entityManager.createQuery("SELECT ID FROM customer WHERE EMAIL =: customerEmail AND PASSWORD =:customerPassword");
			loginQuery.setParameter("customerEmail", customerEmail);
			loginQuery.setParameter("customerPassword", password);

			CustomerEntity customer = (CustomerEntity) loginQuery.getSingleResult();
			return customer;

		} catch (NoResultException e) {
			return null;

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, checkLogin(); FAILED");
		}

	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCustomerExistByEmail(String customerEmail) throws ApplicationException {
		
		try {
			Query existQuery = entityManager.createQuery("SELECT ID FROM customer WHERE EMAIL =:customerEmail");
			existQuery.setParameter("customerEmail", customerEmail);
			CustomerEntity customer = (CustomerEntity) existQuery.getSingleResult();
			if (customer == null) {
				return false;
			}else return true;
				
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,"Error in CustomerDao, isCustomerExistByEmail(); FAILED");
		}

		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCustomerExist(Long customerId) throws ApplicationException {
		
		CustomerEntity customer = null;
		
		try {
			
			customer = entityManager.find(CustomerEntity.class, customerId);
			if (customer == null) {
				return false;
			}else return true;
				
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CustomerDao, isCustomerExistById(); FAILED");
		}
	}
}
