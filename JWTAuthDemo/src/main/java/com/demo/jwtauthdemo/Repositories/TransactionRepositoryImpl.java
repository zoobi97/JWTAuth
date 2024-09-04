package com.demo.jwtauthdemo.Repositories;

import com.demo.jwtauthdemo.Exceptions.EtBadRequestException;
import com.demo.jwtauthdemo.Exceptions.EtResourceNotFoundException;
import com.demo.jwtauthdemo.Model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

    @Autowired
    JdbcTemplate template;

    private static final String SQL_FIND_ALL = "Select t.transaction_id,c.category_id,t.amount,t.note,t.transaction_date " +
            " from et_transactions t left join et_categories c on t.category_id=c.category_id " +
            " left join et_users u on t.user_id=u.user_id order by t.transaction_id";
    private static final String SQL_CREATE= "Insert into " +
            " et_transactions(transaction_id,category_id,user_id,amount,note,transaction_date) " +
            " values(NEXTVAL('et_transactions_seq'),?,?,?,?,?)";
    private static final String SQL_FIND_BY_ID = "Select * from et_transactions where user_id=? and " +
            "category_id=? and transaction_id=?";
    public static final String SQL_UPDATE = "UPDATE et_transactions SET amount = ? WHERE transaction_id = ?";
    public static final String SQL_DELETE="Delete from et_transactions where user_id=? and category_id=? and transaction_id=?";

    @Override
    public List<Transactions> findAll(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        return template.query(SQL_FIND_ALL, new Object[]{userId,categoryId},transactionsRowMapper);
    }

    @Override
    public Transactions findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        try {
            return template.queryForObject(SQL_FIND_BY_ID,new Object[]{userId,categoryId,transactionId},transactionsRowMapper);
        }catch (Exception e){
            throw new EtResourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {

        try {
            KeyHolder holder = new GeneratedKeyHolder();
            template.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,categoryId);
                ps.setInt(2,userId);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                ps.setLong(5,transactionDate);
                return ps;
            },holder);
            return (Integer) holder.getKeys().get("TRANSACTION_ID");
        }catch (Exception e){
            System.out.println("Exception Insert: "+e);
            throw new EtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void Update(Integer userId, Integer categoryId, Integer transactionId, Transactions transactions) throws EtBadRequestException {
        try {
            template.update(SQL_UPDATE,new Object[]{transactions.getAmount(),transactionId});
        }catch (Exception e){
            System.out.println("Exception: "+e);
            throw new EtBadRequestException("Invalid request: "+e);
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) {
        try{
            System.out.println(userId+" "+categoryId+" "+transactionId);
            int count = template.update(SQL_DELETE.toUpperCase(),new Object[]{userId,categoryId,transactionId});
            if(count == 0){
                throw new EtResourceNotFoundException("Record not found");
            }
        }catch (Exception e) {
            throw new EtResourceNotFoundException("Transaction Error: "+e);
        }
    }

    private RowMapper<Transactions> transactionsRowMapper = (rs, rowNum) -> new Transactions(
            rs.getInt("transaction_id"),
            rs.getInt("category_id"),
            rs.getInt("user_id"),
            rs.getDouble("amount"),
            rs.getString("note"),
            rs.getLong("transaction_date")
    );
}
