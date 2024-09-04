package com.demo.jwtauthdemo.Services;

import com.demo.jwtauthdemo.Model.Categories;
import com.demo.jwtauthdemo.Exceptions.*;

import java.util.List;

public interface categoryService{

    List<Categories> fetchAllCategories(Integer userId);
    Categories fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    Categories addCategory(Integer userId,String title, String description) throws EtResourceNotFoundException;
    void updateCategory(Integer userId, Integer categoryId,Categories category) throws EtResourceNotFoundException;
    void removeCategorywithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException;

}
