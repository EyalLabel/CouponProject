package com.couponProject.couponProject.services;

import com.couponProject.couponProject.Exceptions.ErrorMessage;
import com.couponProject.couponProject.Exceptions.SystemException;
import com.couponProject.couponProject.beans.Category;
import com.couponProject.couponProject.beans.Company;
import com.couponProject.couponProject.beans.Coupon;
import com.couponProject.couponProject.repositories.CompanyRepo;
import com.couponProject.couponProject.repositories.CouponRepo;
import com.couponProject.couponProject.repositories.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService extends ClientService {
    private long companyId;

    public long getCompanyId() {
        return companyId;
    }

    public boolean setCompanyId(long companyId) {
        boolean okay;
        if (!(companyRepo.findById(companyId)==null)) {
            okay = true;
            this.companyId = companyId;
        }else {okay=false;}
        return okay;
    }

    public CompanyService(CouponRepo couponRepo, CompanyRepo companyRepo, CustomerRepo customerRepo) {
        super(couponRepo, companyRepo, customerRepo);
    }

    /***
     * Checks if the user details are correct and set the companyID to the ID of the logged company
     * @param email String of email for company
     * @param password String of password for company
     * @return true or false if the log in details are correct
     */
    @Override
    public boolean login(String email, String password) {
        boolean success = false;
        if (companyRepo.existsCompanyByEmailAndPassword(email, password)) {
            companyId = companyRepo.findByEmailAndPassword(email, password).getId();
            success = true;
        }
        return success;
    }

    /***
     * Finds a single coupon by ID using Coupon Repository
     * @param id long ID of requested Coupon
     * @return a single coupon of matching ID
     */
    public Coupon getCoupon(long id) {

        return couponRepo.findById(id);
    }

    /***
     * adds a coupon to Database using Coupon Repository as long as it doesn't exist for the company and sets his company to the logged company
     * @param coupon the requested Coupon
     */
    public boolean addCoupon(Coupon coupon ,long id) {
        boolean okay = true;
        List<Coupon> companyCoupons = companyRepo.findById(id).getCompanyCoupons();
        try {
            for (Coupon coupon1 : companyCoupons) {
                if (coupon1.getTitle().equals(coupon.getTitle())) {
                    okay = false;
                }
            }
            if (okay) {
                coupon.setCompany(companyRepo.findById(id));
                couponRepo.save(coupon);
            } else {
                throw new SystemException(ErrorMessage.COUPON_TITLE_EXISTS);
            }
        } catch (SystemException systemException) {
            System.out.println(systemException.getMessage());
        }return okay;
    }

    /***
     * updates an existing coupon using coupon repository save and flush
     * @param coupon the updated Coupon
     */
    public boolean updateCoupon(Coupon coupon) {
        boolean success;
        try {
            if (couponRepo.existsById(coupon.getId())) {
                Coupon updatedCoupon = couponRepo.findById(coupon.getId());
                updatedCoupon.setAmount(coupon.getAmount());
                updatedCoupon.setCategory(coupon.getCategory());
                updatedCoupon.setDescription(coupon.getDescription());
                updatedCoupon.setEndDate(coupon.getEndDate());
                updatedCoupon.setPrice(coupon.getPrice());
                updatedCoupon.setImage(coupon.getImage());
                updatedCoupon.setStartDate(coupon.getStartDate());
                couponRepo.saveAndFlush(updatedCoupon);
                success=true;
            } else {
                throw new SystemException(ErrorMessage.ID_NOT_FOUND);
            }
        } catch (SystemException systemException) {
            success=false;
            System.out.println(systemException.getMessage());
        }return success;
    }

    /***
     * Deletes company from DB with company repository by id
     * @param id long id of the coupon we wish to delete
     */
    public boolean deleteCoupon(long id) {
        try {
            if (couponRepo.existsById(id)) {
                couponRepo.deleteById(id);
                return true;
            } else {
                System.out.println(id);
                throw new SystemException(ErrorMessage.ID_NOT_FOUND);
            }
        } catch (SystemException systemException) {
            System.out.println(systemException.getMessage());
            return false;
        }
    }

    /***
     * returns all the coupons of the company that is logged in
     * @return List Coupon of all the company's coupons
     */
    public List<Coupon> getCompanyCoupons(long id) {
        try {
            if (companyRepo.existsById(id)) {
                if (couponRepo.findByCompany(companyRepo.findById(id)).isEmpty()) {
                    throw new SystemException(ErrorMessage.COMPANY_HAS_NO_COUPONS);
                }
                return couponRepo.findByCompany(companyRepo.findById(id));
            } else throw new SystemException(ErrorMessage.ID_NOT_FOUND);
        } catch (SystemException systemException) {
            System.out.println(systemException.getMessage());
            return  couponRepo.findByCompany(companyRepo.findById(id));
        }
    }

    /***
     * returns the coupons of the company that is logged in from the specific category
     * @param category the specific category of coupons we want
     * @return List Coupon of the company's coupons from type category
     */
    public List<Coupon> getCompanyCouponsByCategory(Category category,long id) {
        try {
            if (companyRepo.existsById(id)) {
                if (couponRepo.findByCategoryAndCompany(category, companyRepo.findById(id)).isEmpty()) {
                    throw new SystemException(ErrorMessage.COMPANY_HAS_NO_COUPONS);
                }
                return couponRepo.findByCategoryAndCompany(category, companyRepo.findById(id));
            } else throw new SystemException(ErrorMessage.ID_NOT_FOUND);
        } catch (SystemException systemException) {
            System.out.println(systemException.getMessage());
            return couponRepo.findByCategoryAndCompany(category, companyRepo.findById(id));
        }
    }

    /***
     * returns the coupons of the company that is logged in under a specific price
     * @param maxPrice the maximum price of coupons
     * @return List Coupon of the company's coupons under maxPrice
     */
    public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice,long id) {
        try {
            if (companyRepo.existsById(id)) {
                if (couponRepo.findByPriceLessThanAndCompany(maxPrice + 1, companyRepo.findById(id)).isEmpty()) {
                    throw new SystemException(ErrorMessage.COMPANY_HAS_NO_COUPONS);
                }
                return couponRepo.findByPriceLessThanAndCompany(maxPrice + 1, companyRepo.findById(id));
            } else throw new SystemException(ErrorMessage.ID_NOT_FOUND);
        } catch (SystemException systemException) {
            System.out.println(systemException.getMessage());
            return couponRepo.findByPriceLessThanAndCompany(maxPrice + 1, companyRepo.findById(id));
        }
    }

    /***
     * prints out the logged in company's details
     */
    public void getCompanyDetails() {
        System.out.println(companyRepo.findById(companyId));
    }
}
