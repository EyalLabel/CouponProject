package com.couponProject.couponProject.services;

import com.couponProject.couponProject.Exceptions.ErrorMessage;
import com.couponProject.couponProject.Exceptions.SystemException;
import com.couponProject.couponProject.beans.Category;
import com.couponProject.couponProject.beans.Coupon;
import com.couponProject.couponProject.beans.Customer;
import com.couponProject.couponProject.repositories.CompanyRepo;
import com.couponProject.couponProject.repositories.CouponRepo;
import com.couponProject.couponProject.repositories.CustomerRepo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService extends ClientService {
    public CustomerService(CouponRepo couponRepo, CompanyRepo companyRepo, CustomerRepo customerRepo) {
        super(couponRepo, companyRepo, customerRepo);
    }

    private long customerId;

    public long getCustomerId() {
        return customerId;
    }

    /***
     * Checks if the user details are correct and set the customerID to the ID of the logged Customer
     * @param email String of email for customer
     * @param password String of password for customer
     * @return true or false if the log in details are correct
     */
    @Override
    public boolean login(String email, String password) {
        boolean success = false;
        if (customerRepo.existsByEmailAndPassword(email, password)) {
            customerId = customerRepo.findByEmailAndPassword(email, password).getId();
            success = true;
        }
        return success;
    }

    /***
     * Adds a coupon to the customerCoupons list and customer_coupon table
     * @param coupon the coupon that is being purchased
     */
    public boolean purchaseCoupon(Coupon coupon,long id) {

        Customer customer = customerRepo.findById(id);
        List<Coupon> customerCoupons = customer.getCoupons();
        Coupon purchase = null;
        boolean success;
        try {
            if (coupon == null) {
                throw new SystemException(ErrorMessage.COUPON_PURCHASE_ERROR);
            }
            if (couponRepo.existsById(coupon.getId())) {
                purchase = couponRepo.findById(coupon.getId());
            } else {
                throw new SystemException(ErrorMessage.ID_NOT_FOUND);
            }
            boolean okay = false;
            if (!purchase.getEndDate().before(Date.valueOf(LocalDate.now())) && purchase.getAmount() > 0) {
                //if there are no other purchases- purchase can't be a duplicate
                if (customerCoupons.isEmpty()) {
                    okay = true;
                }
                for (Coupon coupon1 : customerCoupons) {
                    if ((coupon1.getCompany().getId() != purchase.getCompany().getId())) {
                        okay = true;
                    } else {
                        if (!coupon1.getTitle().equals(purchase.getTitle())) {
                            okay = true;
                        }
                    }
                }
            }
            if (okay) {
                customerCoupons.add(purchase);
                purchase.setAmount(purchase.getAmount() - 1);
                customer.setCoupons(customerCoupons);
                customerRepo.saveAndFlush(customer);
                couponRepo.saveAndFlush(purchase);
                success=true;
            } else {
                throw new SystemException(ErrorMessage.COUPON_PURCHASE_ERROR);
            }
        } catch (SystemException systemException) {
            success=false;
            System.out.println(systemException.getMessage());
        }return success;
    }

    /***
     * returns all the coupons that were purchased by the customer that is logged in
     * @return List Coupon of customer's Coupons
     */
    public List<Coupon> getAllCustomerCoupons(long id) {
        return customerRepo.findById(id).getCoupons();
    }

    /***
     * returns the coupons of the customer that is logged in from the specific category
     * @param category the specific category of coupons we want
     * @return List Coupon of the customer's coupons of this category
     */
    public List<Coupon> getAllCustomerCouponsByCategory(Category category,long id) {
        List<Coupon> coupons = getAllCustomerCoupons(id);
        try {
            coupons.removeIf(coupon -> !coupon.getCategory().equals(category));
            if (!coupons.isEmpty()) {
                return coupons;
            }else {throw new SystemException(ErrorMessage.NO_COUPONS_OF_CATEGORY);}
        }catch (SystemException systemException){
            System.out.println(systemException.getMessage());
            return coupons;
        }
    }

    /***
     * returns the coupons purchased by the customer that is logged in under a specific price
     * @param maxPrice the maximum price of coupons
     * @return List Coupon  of the customer's coupons under maxPrice
     */
    public List<Coupon> getCustomerCouponsMaxPrice(double maxPrice,long id) {
        List<Coupon> coupons = getAllCustomerCoupons(id);
        try {
            coupons.removeIf(coupon -> coupon.getPrice() > maxPrice);
            if (!coupons.isEmpty()) {
                return coupons;
            }else {throw new SystemException(ErrorMessage.NO_COUPONS_OF_CATEGORY);}
        }catch (SystemException systemException){
            System.out.println(systemException.getMessage());
            return coupons;
        }
    }

    /***
     * prints out the details of the logged in client
     */
    public Customer getCustomerDetails() {
        return(customerRepo.findById(customerId));
    }
}
