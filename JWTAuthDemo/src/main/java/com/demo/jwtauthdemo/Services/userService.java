package com.demo.jwtauthdemo.Services;

import com.demo.jwtauthdemo.Exceptions.EtAuthException;
import com.demo.jwtauthdemo.Model.Users;

public interface userService {

    Users validate(String email, String password) throws EtAuthException;

    Users registerUser(String first_name,String last_name,String email,String password) throws EtAuthException;

}
