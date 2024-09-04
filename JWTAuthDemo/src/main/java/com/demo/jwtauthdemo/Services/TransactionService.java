package com.demo.jwtauthdemo.Services;

import com.demo.jwtauthdemo.Exceptions.EtBadRequestException;
import com.demo.jwtauthdemo.Exceptions.EtResourceNotFoundException;
import com.demo.jwtauthdemo.Model.Transactions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TransactionService {

    List<Transactions> findAllTransactions(Integer userId, Integer categoryId);
    Transactions fetchTransactionById(Integer userId, Integer categoryId,Integer transactionId)
            throws EtResourceNotFoundException;
    Transactions addTransaction(Integer userId,Integer categoryId,Double amount, String note,Long transactionDate)
        throws EtBadRequestException;
    void updateTransaction(Integer userId,Integer categoryId,Integer transactionId,Transactions transactions) throws EtBadRequestException;
    void removeTransaction(Integer userId,Integer categoryId,Integer transactionId) throws EtResourceNotFoundException;


}
