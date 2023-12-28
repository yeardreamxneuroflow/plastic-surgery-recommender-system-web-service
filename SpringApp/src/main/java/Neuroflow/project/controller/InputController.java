package Neuroflow.project.controller;

import Neuroflow.project.dto.ImageContentDto;
import Neuroflow.project.dto.LoadApiContentDto;
import Neuroflow.project.service.JsonService;
import Neuroflow.project.service.LogService;
import Neuroflow.project.service.S3Service;
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
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("service")
public class InputController {

    private final LogService logService;
    private final WebClientService webClientService;
    private final S3Service s3Service;
    private final JsonService jsonService;

    public InputController(LogService logService, WebClientService webClientService, S3Service s3Service, JsonService jsonService) {
        this.logService = logService;
        this.webClientService = webClientService;
        this.s3Service = s3Service;
        this.jsonService = jsonService;
    }

    @GetMapping("/photo")
    public String getForm(){
        return "/projects";
    }

    @PostMapping("/photo")
    public String sendPost(final Model model, @RequestParam("image") MultipartFile multipartFile, Principal principal) throws IOException, SQLException {

        // 입력된 Image를 API로 전송
        String requestData = webClientService.verificationV2(multipartFile);

//        String requestData = "{\"left-eye\":{\"most_similar_wannabe\":\"정국\", \"score\":\"0.9755707\", \"user_landmark_image\":\"binary_code\"}, \"right-eye\":{\"most_similar_wannabe\":\"정국\", \"score\":\"0.9825953\", \"user_landmark_image\":\"binary_code\"}, \"nose\":{\"most_similar_wannabe\":\"박보검\", \"score\":\"0.9825953\", \"user_landmark_image\":\"binary_code\"}, \"lips\":{\"most_similar_wannabe\":\"박보검\", \"score\":\"0.9825953\", \"user_landmark_image\":\"binary_code\"}}";

        byte[] imageData = multipartFile.getBytes();

        String base64ImageData = Base64.getEncoder().encodeToString(imageData);

        Map<String, LoadApiContentDto> jsonObject = jsonService.getObject(requestData);
        Map<String, String> wannabeLandmarkImage = new LinkedHashMap<>();

        for (Map.Entry<String, LoadApiContentDto> entry : jsonObject.entrySet()) {
            wannabeLandmarkImage.put(entry.getKey(), s3Service.getWannabeLandmarkImage(entry.getValue().getWannabeName(), entry.getKey()));
        }

        model.addAttribute("most_similarity_wannabe_image",s3Service.maxSimilarityAndGetImage(jsonObject));

        // 이미지 출력
        model.addAttribute("wannabeLandmarkImage", wannabeLandmarkImage);

        model.addAttribute("client_image", base64ImageData);

        model.addAttribute("image_file", jsonService.getImage(requestData));

        model.addAttribute("jsonObject", jsonObject);


        //
        ImageContentDto inputImageContentDto = s3Service.insertImage(multipartFile, principal.getName());

        //logService
        logService.saveLog(principal.getName(), inputImageContentDto);


        return "/outputimage";
    }

}
