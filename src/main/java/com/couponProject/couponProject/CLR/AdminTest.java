package com.couponProject.couponProject.CLR;

import com.couponProject.couponProject.LoginManager.ClientType;
import com.couponProject.couponProject.LoginManager.LoginManager;
import com.couponProject.couponProject.Thread.DailyDeleteThread;
import com.couponProject.couponProject.beans.Company;
import com.couponProject.couponProject.beans.Customer;
import com.couponProject.couponProject.repositories.CouponRepo;
import com.couponProject.couponProject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class AdminTest implements CommandLineRunner {
    private final LoginManager loginManager;
    private final CouponRepo couponRepo;

    @Override
    public void run(String... args) throws Exception {
        DailyDeleteThread deleteThread = new DailyDeleteThread(couponRepo);
        AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        Company newGoogle = Company.builder()
                .email("google@gmail.com")
                .name("Google")
                .password("456789123")
                .build();
        adminService.addCompany(newGoogle);
        System.out.println(adminService.getSingleCompany(1));
        Company updated = Company.builder()
                .email("google@gmail.com")
                .name("Google")
                .password("123456789")
                .build();
        adminService.updateCompany(updated);
        System.out.println(adminService.getSingleCompany(1));
        Company fakebook = Company.builder()
                .email("Zuckerpunch@hotmail.com")
                .name("Fakebook")
                .password("147852369")
                .build();
        adminService.addCompany(fakebook);
        System.out.println(adminService.getAllCompanies());
        adminService.deleteCompany(2);
        System.out.println(adminService.getAllCompanies());
        Customer customer=Customer.builder()
                .email("juju@gmail.com")
                .password("jazzyjazz")
                .firstName("Zeev")
                .lastName("Mindali")
                .build();
        adminService.addCustomer(customer);
        System.out.println(adminService.getSingleCustomer(1));
        Customer updatedCustomer=Customer.builder()
                .id(1)
                .email("juju@gmail.com")
                .password("javalova")
                .firstName("Zeevik")
                .lastName("Mindali")
                .build();
        adminService.updateCustomer(updatedCustomer);
        System.out.println(adminService.getSingleCustomer(1));
        Customer secondCustomer=Customer.builder()
                .email("udiMabi@gmail.com")
                .password("rakbibi123")
                .firstName("Udi")
                .lastName("Mabi")
                .build();
        adminService.addCustomer(secondCustomer);
        System.out.println(adminService.getAllCustomers());
        adminService.deleteCustomer(2);
        System.out.println(adminService.getAllCustomers());
    }
}
