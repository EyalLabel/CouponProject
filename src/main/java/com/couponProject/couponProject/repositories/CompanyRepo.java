package com.couponProject.couponProject.repositories;

import com.couponProject.couponProject.beans.Company;
import org.hibernate.criterion.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Company findById(long id);

    Company findByEmailAndPassword(String email, String password);

    Company findByName(String name);

    boolean existsCompanyByEmailAndPassword(String email, String password);

    boolean existsById(long id);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    void deleteById(long id);
}
