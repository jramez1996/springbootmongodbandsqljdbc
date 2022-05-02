/**
 * 
 */
//package com.raidentrance.dao;
package com.bezkoder.spring.data.mongodb.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.bezkoder.spring.data.mongodb.model.User;

/**
 * @author raidentrance
 *
 */
@Component
public class UserDao {
	
    @Autowired
    @Qualifier("jdbcSlave")
    private JdbcTemplate jdbcTemplate;
	public List<User> findAll() {
	
		return jdbcTemplate.query("select * from user", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				return new User(rs.getInt("USER_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"));
			}
		});
	}
	/*
	

*/
	/*
	@Autowired
	@Qualifier("jdbcMaster")
	private JdbcTemplate jdbcTemplate;
	public List<User> findAll() {
		return jdbcTemplate.query("select * from user", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				return new User(rs.getInt("USER_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"));
			}
		});
	}*/
}
