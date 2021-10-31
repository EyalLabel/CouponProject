package com.couponProject.couponProject.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Singular
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    @JoinTable(name="customer_coupons",joinColumns=@JoinColumn(name = "customer_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="coupons_id",referencedColumnName = "id"))
    @OnDelete(action= OnDeleteAction.CASCADE)
    private List<Coupon> coupons;
    @Column(unique = true)
    private String email;
    private String password;
}
