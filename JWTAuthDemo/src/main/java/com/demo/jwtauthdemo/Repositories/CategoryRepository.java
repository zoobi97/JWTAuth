package com.demo.jwtauthdemo.Repositories;

import com.demo.jwtauthdemo.Exceptions.EtBadRequestException;
import com.demo.jwtauthdemo.Exceptions.EtResourceNotFoundException;
import com.demo.jwtauthdemo.Model.Categories;

import java.util.List;

public interface CategoryRepository {

    List<Categories> findAll(Integer userId) throws EtResourceNotFoundException;
    Categories findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    Integer create(Integer userId, String title,String description) throws EtBadRequestException;
    void Update(Integer userId, Integer categoryId,Categories category) throws EtBadRequestException;
    void removeById(Integer userId, Integer categoryId);

}
