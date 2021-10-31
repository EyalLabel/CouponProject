package com.couponProject.couponProject.Exceptions;

public enum ErrorMessage {
    COMPANY_NAME("company name is unavailable"),
    COMPANY_EMAIL("company email is unavailable"),
    LOGIN_ERROR("Invalid login details"),
    COUPON_TITLE_EXISTS("The company already has a coupon with the same title"),
    CUSTOMER_EMAIL("Customer Email is already in use"),
    COUPON_PURCHASE_ERROR("Unable to Purchase Coupon"),
    COMPANY_NOT_FOUND("Company not found, Cannot Update"),
    NO_COUPONS_OF_CATEGORY("There are no coupons of this category"),
    INVALID_TOKEN("Token is Invalid, must provide Authorization"),
    COMPANY_HAS_NO_COUPONS("Company has no coupons"),
    ID_NOT_FOUND("Id not found");




    private String description;

    ErrorMessage(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
