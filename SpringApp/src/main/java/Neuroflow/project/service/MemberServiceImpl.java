package Neuroflow.project.service;

import Neuroflow.project.dto.request.MemberJoinDto;
import Neuroflow.project.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean memberSave(MemberJoinDto memberJoinDto) throws SQLException {
        memberJoinDto.setPassword(passwordEncoder.encode(memberJoinDto.getPassword()));
        if (memberRepository.saveUser(memberJoinDto, "ROLE_USER")){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String getMemberId(String username) {
        return memberRepository.getUserId(username);
    }
}
