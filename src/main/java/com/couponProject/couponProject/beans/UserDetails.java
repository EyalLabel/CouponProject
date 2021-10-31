package com.couponProject.couponProject.beans;

import com.couponProject.couponProject.LoginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private String userName;
    private String userPassword;
    private int userId;
   // private String token;
    private String clientType;
}
