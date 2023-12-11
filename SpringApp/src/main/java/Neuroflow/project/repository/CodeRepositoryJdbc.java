package Neuroflow.project.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CodeRepositoryJdbc implements CodeRepository{

    private final JdbcTemplate jdbcTemplate;

    public CodeRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int getSequenceCodeNumber(String code) {
        String sql = "SELECT sequence_number FROM sequence_code WHERE code = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{code}, Integer.class);
    }

    @Override
    public void saveSequenceCodeNumber(String code, int sequence_number) {
        String sql = "UPDATE sequence_code SET sequence_number = (?) WHERE code = (?)";

        jdbcTemplate.update(sql, sequence_number, code);
    }
}
