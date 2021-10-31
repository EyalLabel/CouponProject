package com.couponProject.couponProject.Controllers;

import com.couponProject.couponProject.Swagger.JWTUtil;
import com.couponProject.couponProject.beans.Company;
import com.couponProject.couponProject.beans.Customer;
import com.couponProject.couponProject.beans.UserDetails;
import com.couponProject.couponProject.repositories.CompanyRepo;
import com.couponProject.couponProject.repositories.CustomerRepo;
import com.couponProject.couponProject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("Login")
@CrossOrigin(origins = "http://localhost:3000")
public class JWTLogin {
    private final JWTUtil jwtUtil;
    private final CompanyRepo companyRepo;
    private  final CustomerService customerService;
    private final CustomerRepo customerRepo;


    /***
     * The main log in function! receives a user and checks if the details are valid. returns a new Token
     * @param userDetails either Admin Customer or Company
     * @return new Token
     */
    @PostMapping("login")
    private ResponseEntity<?> userLogin(@RequestBody UserDetails userDetails){
        UserDetails admin=new UserDetails();
        admin.setClientType("ADMINISTRATOR");
        admin.setUserName("admin@admin.com");
        admin.setUserPassword("admin");
        switch (userDetails.getClientType()){
            case ("ADMINISTRATOR"):
                if (userDetails.getUserName().equals(admin.getUserName()) && userDetails.getUserPassword().equals(admin.getUserPassword())){

                    return new ResponseEntity<>(jwtUtil.generateToken(userDetails), HttpStatus.ACCEPTED);
            }else break;
            case ("COMPANY"):
                Company company = companyRepo.findByEmailAndPassword(userDetails.getUserName(), userDetails.getUserPassword());
                if (userDetails.getUserName().equals(company.getEmail()) && userDetails.getUserPassword().equals(company.getPassword())){
                    userDetails.setUserId((int)company.getId());

                    return new ResponseEntity<>(jwtUtil.generateToken(userDetails), HttpStatus.ACCEPTED);}
            case ("CUSTOMER"):
                Customer customer=customerRepo.findByEmailAndPassword(userDetails.getUserName(),userDetails.getUserPassword());
                if (customerService.login(userDetails.getUserName(), userDetails.getUserPassword())){
                    userDetails.setUserId((int)customer.getId());

                    return new ResponseEntity<>(jwtUtil.generateToken(userDetails), HttpStatus.ACCEPTED);
                }

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
