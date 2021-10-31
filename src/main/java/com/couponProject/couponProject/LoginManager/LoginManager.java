package com.couponProject.couponProject.LoginManager;

import com.couponProject.couponProject.Exceptions.ErrorMessage;
import com.couponProject.couponProject.Exceptions.SystemException;
import com.couponProject.couponProject.services.AdminService;
import com.couponProject.couponProject.services.ClientService;
import com.couponProject.couponProject.services.CompanyService;
import com.couponProject.couponProject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginManager {
   private final AdminService adminService;
   private final CompanyService companyService;
   private final CustomerService customerService;

   public ClientService login(String email, String password, ClientType clientType){
       ClientService clientService=null;
       switch (clientType){
           case ADMINISTRATOR:
               clientService=(ClientService) adminService;
               if (adminService.login(email, password)){
                   System.out.println("Welcome Admin");
                   return clientService;
               }
           case CUSTOMER:
               clientService=(ClientService) customerService;
               if (customerService.login(email, password)){
                   System.out.println("Welcome Customer");
                   return clientService;
               }
           case COMPANY:
               clientService=(ClientService) companyService;
               if (companyService.login(email, password)){
                   System.out.println("Welcome Company");
                   return clientService;
               }
           default:
               System.out.println("Invalid Login Info");
               return null;
       }
   }
}
