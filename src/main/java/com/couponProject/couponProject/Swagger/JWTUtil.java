package com.couponProject.couponProject.Swagger;

import com.couponProject.couponProject.LoginManager.ClientType;
import com.couponProject.couponProject.beans.UserDetails;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JWTUtil {
    //type of encryption - סוג של אלגורתים להצפנה
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    //our private key - מפתח ההצפנה שקיים רק אצלנו
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    //create our private key - יצירה של מפתח ההצפנה לשימוש ביצירה של הטוקנים שלנו
    private Key decodedSecrectKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), this.signatureAlgorithm);

    //generate key
    //we need user email, password and role (תפקיד) for create the token
    //since the userDetail is an instace of class, we need to make it hashcode
    //the token need to get clamis which is the information in hashcode
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();        //create new hash map for claims
        claims.put("clientType", userDetails.getClientType());  //insert user type (role)
        claims.put("userId",userDetails.getUserId());
        return createToken(claims, userDetails.getUserName()); //send the subject (email)
    }

    //we create the JWT token from the information that we got.
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();//get current time
        return "Bearer "+Jwts.builder()       //create JWT builder to assist us with JWT creation
                .setClaims(claims)  //set our data (clamis-user,password,role,etc...)
                .setSubject(email)  //set our subject, the first item that we will check
                .setIssuedAt(Date.from(now))  //create issued at , which is current time
                //experation date, which after the date, we need to create a new token
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecrectKey) //sign the token with our secret key
                .compact();   //compact our token, that it will be smaller :)
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException{

        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.decodedSecrectKey)
                .build();
        String myToken=exposeToken(token);
        return jwtParser.parseClaimsJws(myToken).getBody();
    }

    public String extractEmail(String token){
        String myToken=exposeToken(token);
        return extractAllClaims(myToken).getSubject();
    }

    private Date extractExperationDate(String token){
        String myToken=exposeToken(token);
        return extractAllClaims(myToken).getExpiration();
    }

    private boolean isTokenExpired(String token){
        String myToken=exposeToken(token);
        try{

            extractAllClaims(myToken);
            return false;
        } catch (ExpiredJwtException err){
            return true;
        }
    }

    /*
    public boolean validateToken(String token, UserDetails userDetails){
        final String userEmail = extractEmail(token);
        return (userEmail.equals(userDetails.getUserName()) && !isTokenExpired(token));
    }
     */
    public boolean validateToken(String token){
        String myToken=exposeToken(token);
        return (!isTokenExpired(myToken));
    }

    public String getToken(String token){
        if (token.contains("Bearer")){
            return  token.split(" ")[1];
        }
        return token;
    }

    public HttpHeaders getHeaders(String token){
        UserDetails userDetails= new UserDetails();
        userDetails.setUserName(this.extractEmail(token));
        userDetails.setClientType((String) this.extractAllClaims(token).get("clientType"));
        userDetails.setUserId((int)this.extractAllClaims(token).get("userId"));
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.set("Authorization",this.generateToken(userDetails));
        return httpHeaders;
    }
    private String exposeToken(String token) {
        if(token.contains("Bearer")) {
            String myToken[] = token.split(" ");
//            System.out.println(myToken[1]);
            return myToken[1];
        }
        return token;
    }
}

