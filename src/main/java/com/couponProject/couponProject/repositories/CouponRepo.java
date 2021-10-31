package com.couponProject.couponProject.repositories;

import com.couponProject.couponProject.beans.Category;
import com.couponProject.couponProject.beans.Company;
import com.couponProject.couponProject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Transactional
public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    Coupon findById(long id);
    List<Coupon> findByCompany(Company company);
     void deleteById(long id);
     List<Coupon> findByCategoryAndCompany(Category category,Company company);
     List<Coupon> findByPriceLessThanAndCompany(double price,Company company);

     boolean existsById(long id);
     void deleteByEndDate(Date date);
}
