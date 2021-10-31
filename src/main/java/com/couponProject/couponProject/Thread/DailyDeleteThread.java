package com.couponProject.couponProject.Thread;

import com.couponProject.couponProject.beans.Coupon;
import com.couponProject.couponProject.repositories.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class DailyDeleteThread {
    private final CouponRepo couponRepo;

    /***
     * deletes expired coupons every day at 01:00
     */
    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteExpired(){
        List<Coupon> coupons=couponRepo.findAll();
        for (Coupon coupon:coupons){
            if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))){
                couponRepo.deleteById(coupon.getId());
            }
        }
    }
}
