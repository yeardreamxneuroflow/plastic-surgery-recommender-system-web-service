package Neuroflow.project.repository;

import Neuroflow.project.dto.Process;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogRepositoryJdbc implements LogRepository{

    private final JdbcTemplate jdbcTemplate;

    public LogRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveLog(Process process) {
        String logSql = "INSERT INTO service_log(id, member_code, logging_at, image_input_url, image_input_file_key, image_output_url, image_output_file_key) VALUES(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(logSql, process.getId(), process.getMember_id(), process.getLogging_at(), process.getImage_input_url(), process.getImage_input_file_key(), process.getImage_output_url(), process.getImage_output_file_key());
    }
}
