package com.demo.jwtauthdemo.Services;

import com.demo.jwtauthdemo.Repositories.*;
import com.demo.jwtauthdemo.Exceptions.EtAuthException;
import com.demo.jwtauthdemo.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements userService {

    @Autowired
    userRepository repository;

    @Override
    public Users validate(String email, String password) throws EtAuthException {
        if(email != null) email = email.toLowerCase();
        return repository.findByEmailAndPassword(email,password);
    }

    @Override
    public Users registerUser(String first_name, String last_name, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches()){
            throw new EtAuthException("Invalid email format");
        }
        Integer count = repository.getCountByEmail(email);
        if(count>0){
            throw new EtAuthException("Email already registered");
        }
        Integer userId = repository.create(first_name,last_name,email,password);
        return repository.findById(userId);
    }
}
