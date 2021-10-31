package com.couponProject.couponProject.Controllers;

import com.couponProject.couponProject.Exceptions.ErrorMessage;
import com.couponProject.couponProject.Exceptions.SystemException;
import com.couponProject.couponProject.Swagger.JWTUtil;
import com.couponProject.couponProject.beans.Category;
import com.couponProject.couponProject.beans.Company;
import com.couponProject.couponProject.beans.Coupon;
import com.couponProject.couponProject.beans.UserDetails;
import com.couponProject.couponProject.repositories.CompanyRepo;
import com.couponProject.couponProject.repositories.CouponRepo;
import com.couponProject.couponProject.services.CompanyService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController extends ClientController {
    private final CompanyService companySer;
    private final CompanyRepo companyRepo;
    private final CouponRepo couponRepo;
    private final JWTUtil jwtUtil;
    //private static long companyID;

    /***
     * Post= receives a token, a coupon and a company ID and adds the coupon to the company's list
     * @param token
     * @param coupon
     * @param id
     * @return a token
     * @throws SystemException bad token
     */
    @PostMapping("add/{id}") //http://localhost:8080/school/add
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon, @PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companySer.addCoupon(coupon, id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);
    }

    /***
     * Post- receives a token and coupon and updates the coupon in database
     * @param token
     * @param coupon
     * @return token
     * @throws SystemException
     */
    @PostMapping("coupon/update")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companySer.updateCoupon(coupon));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    // ignore this one
    @PostMapping("setLoggedCom")
    public ResponseEntity<?> loggedCom(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companySer.setCompanyId(company.getId()));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     * Get- receives token and Coupon ID and returns the coupon
     * @param token
     * @param id
     * @return Coupon coupon
     * @throws SystemException
     */
    @GetMapping("getCoupon/{id}")
    public ResponseEntity<?> oneCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companySer.getCoupon(id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     * Post- receives token and coupon and deletes the coupon from the database
     * @param token
     * @param coupon
     * @return token
     * @throws SystemException
     */
    @PostMapping("deleteCoupon")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            System.out.println(coupon);
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companySer.deleteCoupon(coupon.getId()));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);
    }

    /***
     * Returns the details of logged in company returns company and token
     * @param token
     * @return Company company and token
     * @throws SystemException
     */
    @GetMapping("getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companyRepo.findById(companySer.getCompanyId()));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     * Get mapping = receives long company ID and returns token adn coupons of matching company
     * @param token
     * @param id
     * @return List of coupons and token
     * @throws SystemException
     */
    @GetMapping("getCompanyCoupons/{id}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companySer.getCompanyCoupons(id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }

    /***
     *Get mapping = receives long company ID,Category and token and returns token and coupons of matching company of requested category
     * @param token
     * @param category
     * @param id
     * @return list of coupons of specific category
     * @throws SystemException
     */
    @GetMapping("getCompanyCouponsByCategory/{category}/{id}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable Category category, @PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        System.out.println("category name:" + category.name());
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companySer.getCompanyCouponsByCategory(category, id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }


    /***
     *Get mapping = receives long company ID,max price and token and returns token and coupons of matching company of requested max price
     * @param token
     * @param maxPrice
     * @param id
     * @return list of coupons of specific max price
     * @throws SystemException
     */
    @GetMapping("getCompanyCouponsByPrice/{maxPrice}/{id}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice, @PathVariable long id) throws SystemException {
        token = token.split(" ")[1];
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(jwtUtil.getHeaders(token))
                    .body(companySer.getCompanyCouponsByMaxPrice(maxPrice, id));
        }
        throw new SystemException(ErrorMessage.INVALID_TOKEN);

    }
}
