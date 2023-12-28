package Neuroflow.project.service;

import Neuroflow.project.dto.ImageContentDto;
import Neuroflow.project.dto.Process;
import Neuroflow.project.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final CodeService codeService;
    private final MemberService memberService;
    private final S3Service s3Service;

    public LogService(LogRepository logRepository, CodeService codeService, MemberService memberService, S3Service s3Service) {
        this.logRepository = logRepository;
        this.codeService = codeService;
        this.memberService = memberService;
        this.s3Service = s3Service;
    }

    public void saveLog(String username, ImageContentDto inputImageContent, ImageContentDto wannabeImageContent){
        // 1. log_id 2. username 3. logging_at 4.image_url,file key
        Process process = new Process(codeService.getSequenceNumber("NFL")
                ,memberService.getMemberId(username)
                ,LocalDateTime.now()
                ,inputImageContent.getUrl()
                ,inputImageContent.getFileKey()
                ,wannabeImageContent.getUrl()
                ,wannabeImageContent.getFileKey());

        logRepository.saveLog(process);
    }
}