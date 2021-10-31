package com.couponProject.couponProject.services;

import com.couponProject.couponProject.Exceptions.ErrorMessage;
import com.couponProject.couponProject.Exceptions.SystemException;
import com.couponProject.couponProject.beans.Company;
import com.couponProject.couponProject.beans.Coupon;
import com.couponProject.couponProject.beans.Customer;
import com.couponProject.couponProject.repositories.CompanyRepo;
import com.couponProject.couponProject.repositories.CouponRepo;
import com.couponProject.couponProject.repositories.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends ClientService {
    public AdminService(CouponRepo couponRepo, CompanyRepo companyRepo, CustomerRepo customerRepo) {
        super(couponRepo, companyRepo, customerRepo);
    }

    /***
     * Logs in to the system by checking email and password
     * @param email String of email for customer/admin/company
     * @param password String of password for customer/admin/company
     * @return true or false if the log in details are correct
     */
    @Override
    public boolean login(String email, String password) {
        return (email.equals("admin@admin.com") && password.equals("admin"));
    }

    /***
     * Updates an existing company with save and flush from company repository
     * @param company the company that we wish to update(Type Company)
     */
    public boolean updateCompany(Company company)  {
        boolean success;
        try {
            if (!companyRepo.existsByName(company.getName())) {
                throw new SystemException(ErrorMessage.COMPANY_NOT_FOUND);
            }else {
                success=true;
                Company updatedCompany = companyRepo.findByName(company.getName());
                updatedCompany.setEmail(company.getEmail());
                updatedCompany.setPassword(company.getPassword());
                updatedCompany.setCompanyCoupons(company.getCompanyCoupons());
                companyRepo.saveAndFlush(updatedCompany);
            }
        }catch (SystemException systemException){
            success=false;
            System.out.println(systemException.getMessage());
        }return success;
    }

    /***
     * Adds company to database using Companies repository
     * @param company the company that we want to add
     */
    public boolean addCompany(Company company)  {
        boolean success;
        try {
            if (companyRepo.existsByName(company.getName())) {

                throw new SystemException(ErrorMessage.COMPANY_NAME);
            }
            if (companyRepo.existsByEmail(company.getEmail())) {

                throw new SystemException(ErrorMessage.COMPANY_EMAIL);
            }
            success=true;
            this.companyRepo.save(company);
        }catch (SystemException systemException){ success=false;System.out.println(systemException.getMessage());}
        return success;
    }

    /***
     * Deletes company from DB with company repository by id
     * @param companyId long id of company that we want to delete
     */
    public boolean deleteCompany(long companyId) {
        boolean success;
        try {
            if (companyRepo.existsById(companyId)) {
                companyRepo.deleteById(companyId);
                success=true;
                System.out.println("Company Deleted Successfully");
            }else {throw new SystemException(ErrorMessage.ID_NOT_FOUND);}
        }catch (SystemException systemException){
            success=false;
            System.out.println(systemException.getMessage()+" could not delete company");
        }return success;
    }

    /***
     * returns a List of all existing companies
     * @return List of Company of all existing companies in DB
     */
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    /***
     * Returns a single company by id with company repository
     * @param companyId long id of the company
     * @return Company from database
     */
    public Company getSingleCompany(long companyId)  {
        try {
            if (companyRepo.existsById(companyId)) {
                return companyRepo.findById(companyId);
            }else {throw new SystemException(ErrorMessage.ID_NOT_FOUND);}
        }catch (SystemException systemException){
            System.out.println(systemException.getMessage());
            return null;
        }
    }

    /***
     * Adds Customer to database using Customer repository
     * @param customer the customer we want to add
     */
    public boolean addCustomer(Customer customer)  {
        boolean success;
        try{

            if (!customerRepo.existsByEmail(customer.getEmail())){
                customerRepo.save(customer);
                System.out.println("Customer Added Successfully");
                success=true;
            }else {
                success=false;
                throw new SystemException(ErrorMessage.CUSTOMER_EMAIL);
            }
        }catch (SystemException systemException){
            success=false;
            System.out.println(systemException.getMessage());
        }
        return success;
    }

    /***
     * Updates an existing Customer to database using Customer repository save and flush
     * @param customer the updated customer
     */
    public boolean updateCustomer(Customer customer) {
        boolean success;
        try {
            if (customerRepo.existsById(customer.getId())){
                Customer updatedCustomer=customerRepo.findById(customer.getId());
                updatedCustomer.setCoupons(customer.getCoupons());
                updatedCustomer.setEmail(customer.getEmail());
                updatedCustomer.setPassword(customer.getPassword());
                updatedCustomer.setFirstName(customer.getFirstName());
                updatedCustomer.setLastName(customer.getLastName());
                customerRepo.saveAndFlush(updatedCustomer);
                success=true;
            }else {success=false;
                throw new SystemException(ErrorMessage.ID_NOT_FOUND);}
        }catch (SystemException systemException){
            success=false;
            System.out.println(systemException.getMessage());
        }return  success;
    }

    /***
     * Deletes customer from DB with customer repository by id
     * @param customerId long id of requested company
     */
    public boolean deleteCustomer(long customerId){
        boolean success;
        try {
            if (customerRepo.existsById(customerId)) {
                customerRepo.deleteById(customerId);
                success=true;
                System.out.println("Customer Deleted Successfully");
            }else {throw new SystemException(ErrorMessage.ID_NOT_FOUND);}
        }catch (SystemException systemException){
            success=false;
            System.out.println(systemException.getMessage()+" could not delete customer");
        }
        return success;
    }

    /***
     * Returns a specific company using it's ID
     * @param customerId the id that will be used by repository
     * @return Customer of matching ID
     */
    public Customer getSingleCustomer(long customerId){
        try{
            if (customerRepo.existsById(customerId)){
                return customerRepo.findById(customerId);
            }else{
                throw new SystemException(ErrorMessage.ID_NOT_FOUND);
            }
        }catch (SystemException systemException){
            System.out.println(systemException.getMessage());
            return null;
        }
    }

    /***
     * Returns a list of all existing customers in Database using repository
     * @return List of Customer of all existing customers
     */
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }
}
