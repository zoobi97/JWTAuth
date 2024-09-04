package com.demo.jwtauthdemo.resources;

import com.demo.jwtauthdemo.Model.Categories;
import com.demo.jwtauthdemo.Model.Transactions;
import com.demo.jwtauthdemo.Services.TransactionService;
import com.demo.jwtauthdemo.Services.TransactionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/categories/{categoryId}/transactions")
public class TransactionResources {

    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping(value = "")
    public ResponseEntity<List<Transactions>> getAllTransactions(HttpServletRequest request,
                                                                 @PathVariable("categoryId") Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        List<Transactions> transactions = transactionService.findAllTransactions(userId,categoryId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping(value = "{transactionId}")
    public ResponseEntity<Transactions> getTransactionById(HttpServletRequest request,
                                                           @PathVariable("categoryId") Integer categoryId,
                                                           @PathVariable("transactionId") Integer transactionId){
        int userId = (Integer) request.getAttribute("userId");
        Transactions transactions = transactionService.fetchTransactionById(userId,categoryId,transactionId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<Transactions> addTransaction(HttpServletRequest request,
                                                       @PathVariable("categoryId") Integer categoryId,
                                                       @RequestBody Map<String,Object> transcationMap){

        int userId = (Integer) request.getAttribute("userId");
        Double amount = Double.valueOf(transcationMap.get("amount").toString());
        String note = (String) transcationMap.get("note");
        Long tDate= Long.valueOf(transcationMap.get("transactionDate").toString());
        Transactions transactions = transactionService.addTransaction(userId,categoryId,amount,note,tDate);
        return new ResponseEntity<> (transactions, HttpStatus.CREATED);

    }

    @PutMapping(value = "/{transactionId}")
    public ResponseEntity<Map<String,Boolean>> updateTransaction(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId, @PathVariable("transactionId") Integer transactionId,
      @RequestBody Transactions transactions){
        int userId = (Integer) request.getAttribute("userId");
        transactionService.updateTransaction(userId,categoryId,transactionId,transactions);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{transactionId}")
    public ResponseEntity<Map<String,Boolean>> deleteTransaction(HttpServletRequest request,
    @PathVariable("categoryId") Integer categoryId, @PathVariable("transactionId") Integer transactionId){
        int userId = (Integer) request.getAttribute("userId");
        transactionService.removeTransaction(userId,categoryId,transactionId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
