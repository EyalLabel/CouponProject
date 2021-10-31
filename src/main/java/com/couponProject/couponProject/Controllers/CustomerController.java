package com.couponProject.couponProject.Controllers;

import com.couponProject.couponProject.Exceptions.ErrorMessage;
import com.couponProject.couponProject.Exceptions.SystemException;
import com.couponProject.couponProject.Swagger.JWTUtil;
import com.couponProject.couponProject.beans.*;
import com.couponProject.couponProject.repositories.CustomerRepo;
import com.couponProject.couponProject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController extends ClientController{
    private final CustomerService customerService;
    private final CustomerRepo customerRepo;
    private final JWTUtil jwtUtil;


    /***
     * Get mapping- returns token and details of logged customer
     * @param token
     * @return token, Customer customer
     * @throws SystemException
     */
    @GetMapping("getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name="Authorization") String token) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(customerRepo.findById(customerService.getCustomerId()));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     * Post- receives token, coupon and customer id and adds the coupon to the list of purchased coupons for this customer
     * @param token
     * @param coupon
     * @param id
     * @return token
     * @throws SystemException
     */
    @PostMapping("purchaseCoupon/{id}")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name="Authorization") String token,@RequestBody Coupon coupon,@PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body( customerService.purchaseCoupon(coupon,id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);


    }

    /***
     * Get- receives long customer ID and returns all the purchased coupons by this customer
     * @param token
     * @param id
     * @return token, List of coupons
     * @throws SystemException
     */
    @GetMapping("getCustomerCoupons/{id}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name="Authorization") String token,@PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(customerService.getAllCustomerCoupons(id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);
    }

    /***
     * GET- receives category,long customer ID and returns all the purchased coupons by this customer of the requested category
     * @param token
     * @param category
     * @param id
     * @return token, List of coupons of matching category
     * @throws SystemException
     */
    @GetMapping("getCustomerCouponsByCategory/{category}/{id}")
    public ResponseEntity<?> getCustomerCouponsCategory(@RequestHeader(name="Authorization") String token,@PathVariable Category category,@PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(customerService.getAllCustomerCouponsByCategory(category,id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     *  Get- receives max price,long customer ID and returns all the purchased coupons by this customer of the requested category
     * @param token
     * @param maxPrice
     * @param id
     * @return token, List of coupons of matching category
     * @throws SystemException
     */
    @GetMapping("getCustomerCouponsByMaxPrice/{maxPrice}/{id}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name="Authorization") String token,@PathVariable double maxPrice,@PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(customerService.getCustomerCouponsMaxPrice(maxPrice,id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }
}
