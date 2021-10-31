package com.couponProject.couponProject.Exceptions;

public class SystemException extends Exception{
    public SystemException(ErrorMessage errorMessage) {
        super(errorMessage.getDescription());
    }
    //String get
}
