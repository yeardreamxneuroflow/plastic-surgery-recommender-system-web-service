package Neuroflow.project.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NamingByTimeServiceImpl implements NamingByTimeService{

    private final MemberService memberService;

    public NamingByTimeServiceImpl(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public String namingByNowTime(String username) {

        LocalDateTime nowtime = LocalDateTime.now();

        String fileKeyDirectory = nowtime.getYear() + "/" + nowtime.getMonth() + "/" + nowtime.getDayOfMonth() + "/";
        String fileKeyName = memberService.getMemberId(username) + nowtime.getHour() + nowtime.getMinute() + nowtime.getSecond();

        return fileKeyDirectory + fileKeyName;
    }

    @Override
    public String namingByNowTimeAndMemberCode() {
        return null;
    }
}

