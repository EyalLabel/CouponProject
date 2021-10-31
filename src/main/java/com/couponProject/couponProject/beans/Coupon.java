package com.couponProject.couponProject.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="coupon")
@Builder
public class Coupon {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @ToString.Exclude
    private Company company;
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private double price;
    private String image;
    private int amount;
    @ManyToMany(mappedBy = "coupons",cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JsonIgnore
    private List<Customer> purchases;

    /***
     * used in deleteCoupon Method to make sure cascade happens
     */
    @PreRemove
    public void removeCouponFromCustomer(){
        for (Customer customer: purchases){
            customer.getCoupons().remove(this);
        }
    }
}
