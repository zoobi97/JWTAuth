package com.demo.jwtauthdemo.resources;

import com.demo.jwtauthdemo.Model.Categories;
import com.demo.jwtauthdemo.Services.categoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryResource {

    @Autowired
    categoryService categoryService;

    @GetMapping(value = "")
    public ResponseEntity<List<Categories>> getAllCategories(HttpServletRequest request){
        int userId = (Integer) request.getAttribute("userId");
        List<Categories> categories = categoryService.fetchAllCategories(userId);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<Categories> getCategoryById(HttpServletRequest request, @PathVariable Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        Categories categories = categoryService.fetchCategoryById(userId,categoryId);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<Categories> addCategory(HttpServletRequest request,
                                                  @RequestBody Map<String,Object> objectMap){

        int userId = (Integer) request.getAttribute("userId");
        String title = (String) objectMap.get("title");
        String description = (String) objectMap.get("description");

        Categories categories = categoryService.addCategory(userId,title,description);
        return new ResponseEntity<> (categories, HttpStatus.CREATED);

    }

    @PutMapping(value = "/{categoryId}")
    public ResponseEntity<Map<String,Boolean>> updateCategory(HttpServletRequest request,
                                                  @PathVariable Integer categoryId,
                                                              @RequestBody Categories categories){
        int userId = (Integer) request.getAttribute("userId");
        categoryService.updateCategory(userId,categoryId,categories);
        Map<String,Boolean> map = new HashMap<>();
        map.put("Success",true);
        return new ResponseEntity<> (map, HttpStatus.OK);

    }

    @DeleteMapping (value = "/{categoryId}")
    public ResponseEntity<Map<String,Boolean>> deleteCategory(HttpServletRequest request,
                                                              @PathVariable Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        categoryService.removeCategorywithAllTransactions(userId,categoryId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("Success",true);
        return new ResponseEntity<> (map, HttpStatus.OK);

    }
}
