package com.leozinn.polling.repository;

import com.leozinn.polling.entities.Users;
import com.leozinn.polling.services.PollingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Users> findBatch(int offset, int batch) {
        String query = "SELECT * FROM users LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, new Object[]{batch, offset}, new UsersRowMapper());
    }

    public int countTotalRecords() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }


    private static class UsersRowMapper implements RowMapper<Users> {
        @Override
        public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
            Users users = new Users();
            users.setId(rs.getInt("id"));
            users.setNome(rs.getString("nome"));
            users.setEmail(rs.getString("email"));
            return users;
        }
    }
}