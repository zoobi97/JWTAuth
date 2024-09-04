package com.demo.jwtauthdemo.resources;

import com.demo.jwtauthdemo.Constants;
import com.demo.jwtauthdemo.Model.Users;
import com.demo.jwtauthdemo.Services.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String ,String>> loginUser(@RequestBody Map<String,Object> userMap){
        String email = (String) userMap.get("email");
        String pass = (String) userMap.get("pass");
        Users users = userService.validate(email,pass);
        return new ResponseEntity<>(generateJWTToken(users), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Map<String ,String>> registerUser(@RequestBody Map<String,Object> userMap){
        String f_name = (String) userMap.get("firstName");
        String l_name = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String pass = (String) userMap.get("pass");
        Users users = userService.registerUser(f_name,l_name,email,pass);
        return new ResponseEntity<>(generateJWTToken(users), HttpStatus.OK);
    }

    private Map<String,String> generateJWTToken(Users users){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp+Constants.TOKEN_VALIDITY))
                .claim("userId",users.getId())
                .claim("first_name",users.getFirst_name())
                .claim("last_name",users.getLast_name())
                .claim("email",users.getEmail())
                .compact();
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return map;
    }
}
