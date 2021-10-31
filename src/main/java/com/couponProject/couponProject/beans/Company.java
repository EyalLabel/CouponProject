package com.couponProject.couponProject.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true,nullable = false)
    private String name;
    @Column(unique = true)
    private String email;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY , mappedBy = "company")
    @ToString.Exclude
    @JsonIgnore
    private List<Coupon> companyCoupons;
    private String password;

    @Override
    public String toString() {
        return "Company{}";
    }
}
