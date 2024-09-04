package com.demo.jwtauthdemo.Repositories;

import com.demo.jwtauthdemo.Exceptions.EtAuthException;
import com.demo.jwtauthdemo.Model.Users;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class userRepositoryImpl implements userRepository{

    private static final String SQL_CREATE="Insert into ET_USERS(user_id,first_name,last_name,email,password) values(NEXTVAL('ET_USERS_SEQ'),?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL="SELECT COUNT(*) FROM et_users WHERE email = ?   ";
    private static final String SQL_FIND_BY_ID="Select * from ET_USERS where user_id=?";

    private static final String SQL_FIND_BY_EMAIL="Select * from et_users where email=?";

    @Autowired
    JdbcTemplate template;
    @Override
    public Integer create(String first_name, String last_name, String email, String password) throws EtAuthException {
        String hashpassword = BCrypt.hashpw(password,BCrypt.gensalt(10));
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            template.update(con -> {
                PreparedStatement statement = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1,first_name);
                statement.setString(2,last_name);
                statement.setString(3,email);
                statement.setString(4,hashpassword);
                return statement;
            },holder);
            return (Integer) holder.getKeys().get("USER_ID");
        }catch (Exception e){
            throw new EtAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public Users findByEmailAndPassword(String email, String password) throws EtAuthException {

        try {
            Users users = template.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
            if(!BCrypt.checkpw(password,users.getPassword())){
                throw new EtAuthException("Invalid password");
            }
            return users;
        }catch (EmptyResultDataAccessException e){
            throw new EtAuthException("Invalid credentials");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return template.queryForObject(SQL_COUNT_BY_EMAIL,new Object[]{email},Integer.class);
    }

    @Override
    public Users findById(Integer UserId) {
        return template.queryForObject(SQL_FIND_BY_ID, new Object[]{UserId}, userRowMapper);
    }

    private RowMapper<Users> userRowMapper = ((rs, rowNum) -> {
        return new Users(
                rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD")
        );
    });
}
