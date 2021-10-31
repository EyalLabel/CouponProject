package com.couponProject.couponProject.Controllers;

import com.couponProject.couponProject.Exceptions.ErrorMessage;
import com.couponProject.couponProject.Exceptions.SystemException;
import com.couponProject.couponProject.Swagger.JWTUtil;
import com.couponProject.couponProject.beans.Company;
import com.couponProject.couponProject.beans.Customer;
import com.couponProject.couponProject.beans.UserDetails;
import com.couponProject.couponProject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class AdminController extends ClientController{
    private final AdminService adminService;
    private final JWTUtil jwtUtil;

/*
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails user) {
        adminService.login(user.getUserName(), user.getUserPassword());
        if (adminService.login(user.getUserName(), user.getUserPassword())){
            return new ResponseEntity<>(jwtUtil.generateToken(user), HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
*/
    /**
     * Post function Receives a token and a company and adds the company to database and returns a valid token
     * @param token
     * @param company
     * @return token
     * @throws SystemException- Bad Token
     */
    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name="Authorization") String token,@RequestBody Company company) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(adminService.addCompany(company));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     *Post function Receives a token and a company and updates the company in database and returns a valid token
     * @param token
     * @param company
     * @return Token
     * @throws SystemException bad token
     */
    @PostMapping("updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name="Authorization") String token,@RequestBody Company company) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(adminService.updateCompany(company));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);
    }

    /***
     * Post Receives company and deletes it from database
     * @param token
     * @param company
     * @return token
     * @throws SystemException bad token
     */
    @PostMapping("deleteCompany")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name="Authorization") String token,@RequestBody Company company) throws SystemException {
        token = token.split(" ")[1];
        System.out.println( company);
        if (adminService.getSingleCompany(company.getId())!=null){
            if (jwtUtil.validateToken(token)) {
                return ResponseEntity.ok()
                        .headers(jwtUtil.getHeaders(token))
                        .body(adminService.deleteCompany(company.getId()));
            }
            throw new SystemException(ErrorMessage.INVALID_TOKEN);

        }throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     *Get mapping- receives token and company ID and returns a valid token and the matching company
     * @param token
     * @param id
     * @return token, Company
     * @throws SystemException bad token
     */
    @GetMapping("getOneCompany/{id}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name="Authorization") String token,@PathVariable long id)throws SystemException{
        token = token.split(" ")[1];
        try{
            if (adminService.getSingleCompany(id)==null){
                throw new SystemException(ErrorMessage.ID_NOT_FOUND);
            }else {
                if (jwtUtil.validateToken(token)) {
                    return ResponseEntity.ok()
                            .headers(jwtUtil.getHeaders(token))
                            .body(adminService.getSingleCompany(id));
                }
            }throw new SystemException(ErrorMessage.INVALID_TOKEN);

        }catch (SystemException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<> (e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    /***
     * Post -receives token and customer and adds him to database
     * @param token
     * @param customer
     * @return token
     * @throws SystemException
     */
    @PostMapping("addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name="Authorization") String token,@RequestBody Customer customer) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(adminService.addCustomer(customer));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     * Post function Receives a token and a customer and updates the customer in database and returns a valid token
     * @param token
     * @param customer
     * @return Token
     * @throws SystemException
     */
    @PostMapping("updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name="Authorization") String token,@RequestBody Customer customer) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(adminService.updateCustomer(customer));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);


    }

    /***
     * Post Receives customer and deletes it from database
     * @param token
     * @param customer
     * @return token
     * @throws SystemException
     */
    @PostMapping("deleteCustomer")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name="Authorization") String token,@RequestBody Customer customer) throws SystemException {
        token = token.split(" ")[1];

            if (adminService.getSingleCustomer(customer.getId())!=null){
                if (jwtUtil.validateToken(token)) {
                    return ResponseEntity.ok()
                            .headers(jwtUtil.getHeaders(token))
                            .body(adminService.deleteCustomer(customer.getId()));
                }
                throw new SystemException(ErrorMessage.INVALID_TOKEN);

            }
        System.out.println(customer);
            throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     * Get mapping returns all the customers receives token
     * @param token
     * @return List of customers and token
     * @throws SystemException
     */
    @GetMapping("getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name="Authorization") String token)  throws SystemException{
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(adminService.getAllCustomers());
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     *Get function gets customer ID and returns token and matching Customer
     * @param token
     * @param id
     * @return token and Customer
     * @throws SystemException
     */
    @GetMapping("getSingleCustomer/{id}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name="Authorization") String token,@PathVariable long id)  throws SystemException{
        token = token.split(" ")[1];
        try{
            if (adminService.getSingleCustomer(id)==null){
                throw new SystemException(ErrorMessage.ID_NOT_FOUND);
            }else {
                if (jwtUtil.validateToken(token)) {
                    return ResponseEntity.ok()
                            .headers(jwtUtil.getHeaders(token))
                            .body(adminService.getSingleCustomer(id));
                }
                throw new SystemException(ErrorMessage.INVALID_TOKEN);
            }

        }catch (SystemException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<> (e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    /***
     *Get- gets all existing companies
     * @param token
     * @return List of all Companies and token
     * @throws SystemException
     */
    @GetMapping("getAllCompanyToken")
    public ResponseEntity<?> getAllCompaniesWtoken(@RequestHeader(name="Authorization") String token) throws SystemException {
        System.out.println(token);
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(adminService.getAllCompanies());
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     *Get- gets all existing Coupons receives token
     * @param token
     * @return List of all coupons and token
     * @throws SystemException
     */
    @GetMapping("getAllCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name="Authorization") String token) throws SystemException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(adminService.getAllCoupons());
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }
}
