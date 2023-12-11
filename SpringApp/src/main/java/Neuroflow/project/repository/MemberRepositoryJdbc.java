package Neuroflow.project.repository;

import Neuroflow.project.dto.request.MemberJoinDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class MemberRepositoryJdbc implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUser(MemberJoinDto user, String auth) throws SQLException {
        String sql = "INSERT INTO users (id, username, password, enabled, created_at) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword(), 1, user.getCreated_at());

        String auth_sql = "INSERT INTO authorities (username, authority) VALUES(?, ?)";
        jdbcTemplate.update(auth_sql, user.getUsername(), user.getRole());
    }
}
