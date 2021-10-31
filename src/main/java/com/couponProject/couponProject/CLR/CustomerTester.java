package com.couponProject.couponProject.CLR;

import com.couponProject.couponProject.LoginManager.ClientType;
import com.couponProject.couponProject.LoginManager.LoginManager;
import com.couponProject.couponProject.beans.Category;
import com.couponProject.couponProject.repositories.CouponRepo;
import com.couponProject.couponProject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class CustomerTester implements CommandLineRunner {
    private final LoginManager loginManager;
    private final CouponRepo couponRepo;

    @Override
    public void run(String... args) throws Exception {
        CustomerService customerService = (CustomerService) loginManager.login("juju@gmail.com", "javalova", ClientType.CUSTOMER);
        customerService.purchaseCoupon(couponRepo.findById(1),1);
        customerService.purchaseCoupon(couponRepo.findById(3),1);
        System.out.println(customerService.getAllCustomerCoupons(1));
        System.out.println(customerService.getAllCustomerCouponsByCategory(Category.VACATION,1));
        System.out.println(customerService.getCustomerCouponsMaxPrice(60,1));
        couponRepo.deleteById(3);
        System.out.println(customerService.getAllCustomerCoupons(1));
        customerService.getCustomerDetails();
    }
}
