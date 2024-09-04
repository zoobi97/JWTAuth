package com.demo.jwtauthdemo.Services;

import com.demo.jwtauthdemo.Exceptions.EtBadRequestException;
import com.demo.jwtauthdemo.Exceptions.EtResourceNotFoundException;
import com.demo.jwtauthdemo.Model.Transactions;
import com.demo.jwtauthdemo.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public List<Transactions> findAllTransactions(Integer userId, Integer categoryId) {
        return transactionRepository.findAll(userId,categoryId);
    }

    @Override
    public Transactions fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        return transactionRepository.findById(userId,categoryId,transactionId);
    }

    @Override
    public Transactions addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        int tId=  transactionRepository.create(userId, categoryId, amount, note, transactionDate);
        return transactionRepository.findById(userId,categoryId,tId);
    }

    @Override
    public void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transactions transactions) throws EtBadRequestException {
        transactionRepository.Update(userId,categoryId,transactionId,transactions);
    }

    @Override
    public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        transactionRepository.removeById(userId,categoryId,transactionId);
    }
}
