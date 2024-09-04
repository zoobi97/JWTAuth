package com.demo.jwtauthdemo.Repositories;

import com.demo.jwtauthdemo.Exceptions.EtBadRequestException;
import com.demo.jwtauthdemo.Exceptions.EtResourceNotFoundException;
import com.demo.jwtauthdemo.Model.Categories;
import com.demo.jwtauthdemo.Model.Transactions;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TransactionRepository {
    List<Transactions> findAll(Integer userId,Integer categoryId) throws EtResourceNotFoundException;
    Transactions findById(Integer userId, Integer categoryId, Integer transactionId)
            throws EtResourceNotFoundException;
    Integer create(Integer userId, Integer categoryId, Double amount,String note, Long transactionDate)
            throws EtBadRequestException;
    void Update(Integer userId, Integer categoryId,Integer transactionId,Transactions transactions)
            throws EtBadRequestException;
    void removeById(Integer userId, Integer categoryId,Integer transactionId);
}
