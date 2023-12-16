package Neuroflow.project.service;

import jakarta.annotation.PostConstruct;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {
    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.create("http://13.209.213.35:5000");
    }


    public byte[] verification(MultipartFile multipartFile) {
        webClient = WebClient.create("http://13.209.213.35:5000");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("img_file", multipartFile.getResource());
        var payloadFile = builder.build();

        return webClient.post()
                .uri("/recommend")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(payloadFile))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    public JSONObject verificationV2(MultipartFile multipartFile) {
        webClient = WebClient.create("http://3.37.43.33:5000");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("img_file", multipartFile.getResource());
        var payloadFile = builder.build();

        return webClient.post()
                .uri("/recommend")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(payloadFile))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
    }
}
