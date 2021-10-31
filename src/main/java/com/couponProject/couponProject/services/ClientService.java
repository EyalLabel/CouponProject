package com.couponProject.couponProject.services;

import com.couponProject.couponProject.repositories.CompanyRepo;
import com.couponProject.couponProject.repositories.CouponRepo;
import com.couponProject.couponProject.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


@RequiredArgsConstructor
public abstract class ClientService {
protected final CouponRepo couponRepo;
protected final CompanyRepo companyRepo;
protected final CustomerRepo customerRepo;

    public boolean login(String email, String password) {
        String rightMail = null, rightPassword = null;
        return (email.equals(rightMail) && password.equals(rightPassword));
    }
}
