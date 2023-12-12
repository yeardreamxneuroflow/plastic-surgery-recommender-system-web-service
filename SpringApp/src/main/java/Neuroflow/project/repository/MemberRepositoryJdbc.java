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
    public boolean saveUser(MemberJoinDto user, String auth) {

        String sql = "INSERT INTO users (id, username, password, enabled, created_at) VALUES(?,?,?,?,?)";
        try {
            jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword(), 1, user.getCreated_at());
        } catch (Exception e){
            System.out.println("아이디 중복오류");
            return false;
        }
        String auth_sql = "INSERT INTO authorities (username, authority) VALUES(?, ?)";
        jdbcTemplate.update(auth_sql, user.getUsername(), user.getRole());

        return true;
    }

    @Override
    public String getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, String.class);
    }
}
