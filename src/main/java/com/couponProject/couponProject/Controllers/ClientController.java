package com.couponProject.couponProject.Controllers;


import com.couponProject.couponProject.LoginManager.LoginManager;
import com.couponProject.couponProject.beans.UserDetails;
import com.couponProject.couponProject.beans.UserDetails;
import com.couponProject.couponProject.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Client")
@NoArgsConstructor
public abstract class ClientController {
  private LoginManager loginManager;
   // @PostMapping("Login")
    /*
    public ResponseEntity<?> login(@RequestBody UserDetails user) {
        ClientService clientService=loginManager.login(user.getUserName(), user.getUserPassword(),user.getClientType());
        boolean success;
        if (clientService!=null)
            success=true;
        else success=false;
        return new ResponseEntity<>(success,HttpStatus.ACCEPTED);

    }
*/
    public ClientController(LoginManager loginManager) {
        this.loginManager = loginManager;
    }
}
