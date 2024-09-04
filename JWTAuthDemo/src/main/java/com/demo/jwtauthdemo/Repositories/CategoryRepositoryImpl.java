package com.demo.jwtauthdemo.Repositories;

import com.demo.jwtauthdemo.Exceptions.EtBadRequestException;
import com.demo.jwtauthdemo.Exceptions.EtResourceNotFoundException;
import com.demo.jwtauthdemo.Model.Categories;
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
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    JdbcTemplate template;
    public static final String SQL_CREATE = "Insert into et_categories(category_id,user_id,title,description) values (NEXTVAL('et_categories_seq'), ?,?,?)";
    public static final String SQL_FIND_BY_ID = "SELECT c.*, COALESCE(SUM(t.amount), 0) AS TOTAL_EXPENSE " +
            "FROM et_categories c " +
            "LEFT JOIN et_transactions t ON c.category_id = t.category_id " +
            "WHERE c.category_id = ? AND c.user_id = ? " +
            "GROUP BY c.category_id";
    public static final String SQL_FIND_ALL_CATEGORIES = "SELECT c.*, COALESCE(SUM(t.amount), 0) " +
            "AS TOTAL_EXPENSE FROM et_categories c LEFT JOIN et_transactions t ON c.category_id = t.category_id " +
            " WHERE c.user_id = ? GROUP BY c.category_id";
    public static final String SQL_UPDATE_CATEGORY = "Update et_categories set title=?, description=?" +
            " where user_id=? and category_id=?";
    public static final String SQL_DELETE_TRANSACTIONS="Delete from et_transactions where category_id=?";
    public static final String SQL_DELETE = "delete from et_categories where user_id=? and category_id=?";

    @Override
    public List<Categories> findAll(Integer userId) throws EtResourceNotFoundException {
        return template.query(SQL_FIND_ALL_CATEGORIES, new Object[]{userId}, categoryRowMapper);
    }

    @Override
    public Categories findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        try {
            return template.queryForObject(SQL_FIND_BY_ID, new Object[]{categoryId, userId}, categoryRowMapper);
        } catch (Exception e) {
            System.out.println("Exception findById: " + e);
            throw new EtResourceNotFoundException("Category not found");
        }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws EtBadRequestException {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            template.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, description);
                return ps;
            }, holder);
            System.out.println("Returned id: " + holder.getKeys().get("category_id"));
            return (Integer) holder.getKeys().get("category_id");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void Update(Integer userId, Integer categoryId, Categories category) throws EtBadRequestException {
        try {
            template.update(SQL_UPDATE_CATEGORY, category.getTitle(), category.getDescription(),
                    userId, categoryId);
        } catch (Exception e) {
            System.out.println("Exception Update: " + e);
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {
        this.removeAllTransactions(categoryId);
        template.update(SQL_DELETE, new Object[]{userId, categoryId});
    }

    public void removeAllTransactions(Integer categoryId) {
        template.update(SQL_DELETE_TRANSACTIONS, new Object[]{categoryId});
    }

    private RowMapper<Categories> categoryRowMapper = (rs, rowNum) -> new Categories(
            rs.getInt("category_id"),
            rs.getInt("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getDouble("total_expense")
    );
}
