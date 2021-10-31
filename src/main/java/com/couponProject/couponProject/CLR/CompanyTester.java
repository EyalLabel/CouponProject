package com.couponProject.couponProject.CLR;

import com.couponProject.couponProject.LoginManager.ClientType;
import com.couponProject.couponProject.LoginManager.LoginManager;
import com.couponProject.couponProject.beans.Category;
import com.couponProject.couponProject.beans.Coupon;
import com.couponProject.couponProject.repositories.CouponRepo;
import com.couponProject.couponProject.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Order(2)
@RequiredArgsConstructor
public class CompanyTester implements CommandLineRunner {
    private final LoginManager loginManager;
    @Override
    public void run(String... args) throws Exception {
        CompanyService companyService= (CompanyService) loginManager.login("google@gmail.com","123456789", ClientType.COMPANY);
        Coupon coupon=Coupon.builder()
                .title("Free milkshake")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.of(2022,4,15)))
                .price(13.6)
                .image("milkshake.png")
                .description("Tasty milkshake extra creamy")
                .category(Category.FOOD)
                .amount(6)
                .build();
        companyService.addCoupon(coupon,1);
        System.out.println(companyService.getCompanyCoupons(1));
        Coupon updatedCoupon=Coupon.builder()
                .id(1)
                .title("Free milkshake")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.of(2022,8,15)))
                .price(10)
                .image("https://preppykitchen.com/wp-content/uploads/2021/04/Milkshake-Recipe-Card.jpg")
                .description("Tasty milkshake extra creamy now even cheaper!")
                .category(Category.FOOD)
                .amount(6)
                .build();
        companyService.updateCoupon(updatedCoupon);
        System.out.println(companyService.getCompanyCoupons(1));
        Coupon newCoupon=Coupon.builder()
                .title("Iphone Discount")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.of(2021,10,15)))
                .price(50)
                .image("https://static-www.o2.co.uk/sites/default/files/iphone-13-blue-sku-header-141021.png")
                .description("20% off for smartphones")
                .category(Category.ELECTRICITY)
                .amount(3)
                .build();
        companyService.addCoupon(newCoupon,1);
        Coupon coupon3=Coupon.builder()
                .title("Dead sea visit")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.of(2022,01,04)))
                .price(150)
                .image("https://mybestplace.com/uploads/2021/02/Isola-di-sale-Mar-Morto-2.jpg")
                .description("A short visit to the dead sea")
                .category(Category.VACATION)
                .amount(6)
                .build();
        Coupon coupon4=Coupon.builder()
                .title("3 Hats")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.of(2021,11,20)))
                .price(40)
                .image("https://www.insidehook.com/wp-content/uploads/2021/03/hats.jpg?fit=1200%2C800")
                .description("3 hats for the price of 2")
                .category(Category.FASHION)
                .amount(15)
                .build();
        companyService.addCoupon(coupon3,1);
        companyService.addCoupon(coupon4,1);
        System.out.println(companyService.getCompanyCoupons(1));
        System.out.println(companyService.getCompanyCouponsByCategory(Category.FOOD,1));
        System.out.println(companyService.getCompanyCouponsByCategory(Category.ELECTRICITY,1));
        System.out.println(companyService.getCompanyCouponsByMaxPrice(45,1));
        companyService.deleteCoupon(2);
        System.out.println(companyService.getCompanyCoupons(1));
        companyService.getCompanyDetails();
    }
}
