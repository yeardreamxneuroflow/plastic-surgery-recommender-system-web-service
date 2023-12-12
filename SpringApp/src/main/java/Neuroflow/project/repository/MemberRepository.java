package Neuroflow.project.repository;

import Neuroflow.project.dto.request.MemberJoinDto;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface MemberRepository {
    public boolean saveUser(MemberJoinDto user, String auth) throws SQLException;

    public String getUserId(String username);
}
