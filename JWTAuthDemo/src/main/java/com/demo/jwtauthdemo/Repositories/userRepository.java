package com.demo.jwtauthdemo.Repositories;

import com.demo.jwtauthdemo.Exceptions.EtAuthException;
import com.demo.jwtauthdemo.Model.Users;

public interface userRepository {

    Integer create(String first_name,String last_name,String email,String password) throws EtAuthException;

    Users findByEmailAndPassword(String email,String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    Users findById(Integer UserId);
}
