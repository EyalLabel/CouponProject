package com.couponProject.couponProject.repositories;


import com.couponProject.couponProject.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    boolean existsByEmailAndPassword(String email,String password);
    Customer findById(long id);
    Customer findByEmailAndPassword(String email,String password);
    boolean existsByEmail(String email);
    boolean existsById(long id);
    void deleteById(long id );
}
