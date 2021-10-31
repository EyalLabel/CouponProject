ALTER TABLE `springcoupondb`.`customer_coupons`
ADD CONSTRAINT `FK3ra7y4e2fu00kui0lby4mj0w1`
FOREIGN KEY (`coupons_id`)
REFERENCES `springcoupondb`.`coupon`(`id`)
ON DELETE CASCADE;