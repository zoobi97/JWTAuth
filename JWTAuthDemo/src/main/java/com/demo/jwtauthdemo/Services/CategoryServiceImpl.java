package com.demo.jwtauthdemo.Services;

import com.demo.jwtauthdemo.Exceptions.EtResourceNotFoundException;
import com.demo.jwtauthdemo.Model.Categories;
import com.demo.jwtauthdemo.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements categoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Categories> fetchAllCategories(Integer userId) {
        return categoryRepository.findAll(userId);
    }

    @Override
    public Categories fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public Categories addCategory(Integer userId, String title, String description) throws EtResourceNotFoundException {
        int categoryId = categoryRepository.create(userId, title, description);
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Categories category) throws EtResourceNotFoundException {
        categoryRepository.Update(userId, categoryId, category);
    }

    @Override
    public void removeCategorywithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        this.fetchCategoryById(userId,categoryId);
        categoryRepository.removeById(userId, categoryId);
    }
}
