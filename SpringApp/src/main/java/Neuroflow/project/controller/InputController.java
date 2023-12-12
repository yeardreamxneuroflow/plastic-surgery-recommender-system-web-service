package Neuroflow.project.controller;

import Neuroflow.project.service.LogService;
import Neuroflow.project.service.WebClientService;
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

@Controller
@RequestMapping("service")
public class InputController {

    private final LogService logService;
    private final WebClientService webClientService;

    public InputController(LogService logService, WebClientService webClientService) {
        this.logService = logService;
        this.webClientService = webClientService;
    }

    @GetMapping("/photo")
    public String getForm(){
        return "/projects";
    }

    @PostMapping("/photo")
    public String sendPost(final Model model, @RequestParam("image") MultipartFile multipartFile, Principal principal) throws IOException, SQLException {

        // 입력된 IMAGE를 API로 전송
        byte[] imageData = webClientService.verification(multipartFile);



        String base64ImageData = Base64.getEncoder().encodeToString(imageData);

        // 이미지 출력
        model.addAttribute("image_file", base64ImageData);

        //logService
        logService.saveLog(principal.getName());

        return "/image_output";
    }

}
