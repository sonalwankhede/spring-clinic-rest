package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.Role;
import org.com.drSnehalAyuCareClinic.model.UserModel;
import org.com.drSnehalAyuCareClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcUserRepositoryImpl implements UserRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertUser = new SimpleJdbcInsert(dataSource).withTableName("users");
    }

    @Override
    public void save(UserModel user) throws DataAccessException {

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        try {
            getByUsername(user.getUsername());
            this.namedParameterJdbcTemplate.update("UPDATE users SET password=:password, enabled=:enabled WHERE username=:username", parameterSource);
        } catch (EmptyResultDataAccessException e) {
            this.insertUser.execute(parameterSource);
        } finally {
            updateUserRoles(user);
        }
    }

    @Override
	public UserModel getByUsername(String username) {

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return this.namedParameterJdbcTemplate.queryForObject("SELECT * FROM users WHERE username=:username",
            params, BeanPropertyRowMapper.newInstance(UserModel.class));
    }

    private void updateUserRoles(UserModel user) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        this.namedParameterJdbcTemplate.update("DELETE FROM roles WHERE username=:username", params);
        for (Role role : user.getRoles()) {
            params.put("role", role.getName());
            if (role.getName() != null) {
                this.namedParameterJdbcTemplate.update("INSERT INTO roles(username, role) VALUES (:username, :role)", params);
            }
        }
    }
}
