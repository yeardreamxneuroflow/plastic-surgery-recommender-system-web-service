package Neuroflow.project.service;

import org.springframework.stereotype.Service;

@Service
public interface NamingByTimeService {
    public String namingByNowTime(String username);

    public String namingByNowTimeAndMemberCode();
}
