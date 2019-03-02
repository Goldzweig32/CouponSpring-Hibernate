package com.yaniv.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yaniv.coupons.beans.Coupon;
import com.yaniv.coupons.beans.CouponEntity;
import com.yaniv.coupons.beans.CustomerCouponEntity;
import com.yaniv.coupons.enums.CouponType;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.DateUtils;
import com.yaniv.coupons.utils.JdbcUtils;

@Repository
public class CouponDao {

	@PersistenceContext(unitName="couponHibernate")
	private EntityManager entityManager;

	@Transactional(propagation=Propagation.REQUIRED)
	public void createCoupon(CouponEntity coupon) throws ApplicationException {

		try {
			entityManager.persist(coupon);
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, creatCoupon(); FAILED");
		}
	}

	public CouponEntity getCoupon(long couponId) throws ApplicationException {

		try {
			return entityManager.find(CouponEntity.class, couponId);

		} catch (NoResultException e) {
			throw new ApplicationException(e, ErrorType.COUPON_ID_DOES_NOT_EXIST,
					"Error in CouponDao, getCoupon(); FAILED");
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, getCoupon(); FAILED");
		}
	}


	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCouponsFromCustomerCouponByCouponId(long couponId) throws ApplicationException {



		try {
			Query deleteFromCustomerCoupon = entityManager.createNativeQuery("DELETE FROM customer_coupon WHERE COUPON_ID =:couponId");
			deleteFromCustomerCoupon.setParameter("couponId", couponId);

			deleteFromCustomerCoupon.executeUpdate();
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, deleteCouponFromCustomerCouponByCouponId(); FAILED");
		}



	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCouponsFromCustomerCouponByCustomerId(long customerId) throws ApplicationException {

		try {

			Query deleteFromCoustomerCouponByCustomer = entityManager.createNativeQuery("DELETE FROM customer_coupon WHERE CUSTOMER_ID =:customerId");
			deleteFromCoustomerCouponByCustomer.setParameter("customerId", customerId);

			deleteFromCoustomerCouponByCustomer.executeUpdate();

		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, deleteCouponFromCustomerCouponByCustomerId(); FAILED");
		}

	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCoupon(long couponId) throws ApplicationException {



		try {

			deleteCouponsFromCustomerCouponByCouponId(couponId);
			CouponEntity coupon = getCoupon(couponId);
			entityManager.remove(coupon);
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, deleteCoupon(); FAILED");
		}

	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCoupon(CouponEntity coupon) throws ApplicationException {

		try {
			entityManager.merge(coupon);
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, updateCoupon(); FAILED");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCoupons() throws ApplicationException {

		try {

			Query getCouponsQuery = entityManager.createNativeQuery("SELECT * FROM coupon");
			List<CouponEntity> coupons = getCouponsQuery.getResultList();
			return coupons;
			
		} catch (NoResultException e) {
			return null;

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getAllCoupons(); FAILED");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsByType(CouponType couponType) throws ApplicationException {

		try {

			Query getCouponsByTypeQ = entityManager.createNativeQuery("SELECT * FROM coupon WHERE COUPON_TYPE =:couponType");
			getCouponsByTypeQ.setParameter("couponType", couponType);
			return getCouponsByTypeQ.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponByType(); FAILED");
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsUpToPrice(double price) throws ApplicationException {

		try {

			Query getCouponsUpToPriceQ = entityManager.createNativeQuery("SELECT * FROM coupon WHERE COUPON_PRICE <=:couponPrice");
			getCouponsUpToPriceQ.setParameter("couponPrice", price);

			return getCouponsUpToPriceQ.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, getCouponsUpToPrice(); FAILED");
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponUpToDate(String couponEndDate) throws ApplicationException {

		try {

			Query getCouponUpToDateQ = entityManager.createNativeQuery("SELECT * FROM coupon WHERE END_DATE <=:couponEndDate");
			getCouponUpToDateQ.setParameter("couponEndDate", couponEndDate);

			return getCouponUpToDateQ.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, getCouponUpToDate(); FAILED");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsByCustomerId(long customerId) throws ApplicationException {

		try {

			Query getCouponsByCustomerIdQ = entityManager.createNativeQuery("SELECT ID,COUPON_TITLE,START_DATE,END_DATE,AMOUNT,COUPON_TYPE,COUPON_MESSAGE,COUPON_PRICE,COUPON_IMAGE,COMPANY_ID "
					+ " FROM coupon c inner" + " JOIN customer_coupon cc on c.ID = cc.COUPON_ID "
					+ " WHERE cc.CUSTOMER_ID =:customerIdd ");
			getCouponsByCustomerIdQ.setParameter("customerIdd", customerId);

			return getCouponsByCustomerIdQ.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, getCouponsByCustomerId(); FAILED");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsByCompany(long companyId) throws ApplicationException {

		try {

			Query getCouponsByCompanyQ = entityManager.createNativeQuery("SELECT ID,COUPON_TITLE,START_DATE,END_DATE,AMOUNT,COUPON_TYPE,COUPON_MESSAGE,COUPON_PRICE,COUPON_IMAGE,COMPANY_ID "
					+ " FROM coupon"
					+ " WHERE COMPANY_ID =:companyIdd");
			getCouponsByCompanyQ.setParameter("companyIdd", companyId);

			return getCouponsByCompanyQ.getResultList();

		} catch (NoResultException e) {
			return null;

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, getCouponsByCustomerId(); FAILED");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCouponExistByTitle(String couponTitle) throws ApplicationException {

		try {

			Query isCouponExistByTitleQ = entityManager.createNativeQuery("SELECT ID FROM coupon WHERE COUPON_TITLE =:couponTitle");
			isCouponExistByTitleQ.setParameter("couponTitle", couponTitle);

			CouponEntity coupon = (CouponEntity)isCouponExistByTitleQ.getSingleResult();

			if (coupon != null) {
				return true;
			} else return false;

		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, isCouponExistByTitle(); FAILED");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCouponExist(Long couponId) throws ApplicationException {

		try {
			CouponEntity coupon = entityManager.find(CouponEntity.class, couponId);

			if (coupon != null) {
				return true;
			} else return false;

		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, isCouponExistById(); FAILED");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteExpiredCoupons() throws ApplicationException {

		

		try {
			
			Query deleteExpiredCouponsQ = entityManager.createNativeQuery("DELETE FROM customer_coupon WHERE COUPON_ID IN (SELECT ID FROM coupon WHERE END_DATE <:endDate1);"
					+ "DELETE FROM coupon WHERE ID IN (SELECT ID FROM coupon WHERE END_DATE <:endDate2)");
			deleteExpiredCouponsQ.setParameter("endDate1", DateUtils.getCurrentDate());
			deleteExpiredCouponsQ.setParameter("endDate2", DateUtils.getCurrentDate());
			
			deleteExpiredCouponsQ.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, deleteExpiredCoupons(); FAILED");
		}

	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void purchaseCoupon(long customerId, long couponId) throws ApplicationException {

		try {
			
			CustomerCouponEntity customerCoupon = new CustomerCouponEntity();
			
			customerCoupon.setCustomerId(customerId);
			customerCoupon.setCouponId(couponId);
			
			entityManager.persist(customerCoupon);

		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, purchaseCoupon(); FAILED");
		}

	}

}
