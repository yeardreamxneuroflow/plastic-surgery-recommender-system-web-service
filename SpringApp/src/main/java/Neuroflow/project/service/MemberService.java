package Neuroflow.project.service;

import Neuroflow.project.dto.request.MemberJoinDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public interface MemberService {

    public boolean memberSave(MemberJoinDto memberJoinDto) throws SQLException;

    public String getMemberId(String username);
}
