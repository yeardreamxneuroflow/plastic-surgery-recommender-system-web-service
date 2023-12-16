package Neuroflow.project.controller;

import Neuroflow.project.dto.ImageContentDto;
import Neuroflow.project.dto.LoadApiContentDto;
import Neuroflow.project.service.LogService;
import Neuroflow.project.service.S3Service;
import Neuroflow.project.service.WebClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Map;

@Controller
@RequestMapping("service")
public class InputController {

    private final LogService logService;
    private final WebClientService webClientService;
    private final S3Service s3Service;

    public InputController(LogService logService, WebClientService webClientService, S3Service s3Service) {
        this.logService = logService;
        this.webClientService = webClientService;
        this.s3Service = s3Service;
    }

    @GetMapping("/photo")
    public String getForm(){
        return "/projects";
    }

    @PostMapping("/photo")
    public String sendPost(final Model model, @RequestParam("image") MultipartFile multipartFile, Principal principal) throws IOException, SQLException {

        // new API JSON array 처리. V1
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        LoadApiContentDto loadApiContentDto = objectMapper.readValue(webClientService.verification(multipartFile), LoadApiContentDto.class);

        // new API JSON array 처리. V2
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Map<String, LoadApiContentDto> loadApiContentDtoMap = objectMapper.readValue(webClientService.verification(multipartFile), Map.class);

        // 입력된 IMAGE를 API로 전송

        byte[] imageData = webClientService.verification(multipartFile);

        System.out.println(imageData);
        String base64ImageData = Base64.getEncoder().encodeToString(imageData);

        // 이미지 출력
        model.addAttribute("image_file", base64ImageData);

        //
        ImageContentDto inputImageContentDto = s3Service.insertImage(multipartFile, principal.getName());

        //logService
        logService.saveLog(principal.getName(), inputImageContentDto);

        return "/image_output";
    }

    @GetMapping("/image")
    public String imagePost(){
        return "/outputimage";
    }
}
