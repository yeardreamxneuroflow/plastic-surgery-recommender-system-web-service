package Neuroflow.project.service;

import Neuroflow.project.dto.FacialFeatures;
import Neuroflow.project.dto.JsonArray;
import Neuroflow.project.dto.LoadApiContentDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JsonService {

    private final WebClientService webClientService;

    public JsonService(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    @JsonIgnoreProperties
    public String getScore(String jsonData) throws JsonProcessingException {
        jsonData = jsonData.replace("left-eye","leftEye");
        jsonData = jsonData.replace("right-eye","rightEye");
        ObjectMapper objectMapper = new ObjectMapper();
        FacialFeatures facialFeatures = objectMapper.readValue(jsonData, FacialFeatures.class);

        return facialFeatures.getNose().getMost_similar_wannabe();
    }

    public String getWannabe(String jsonData){

        return "";
    }

    public String getImage(String jsonData) throws JsonProcessingException {
        jsonData = jsonData.replace("left-eye","leftEye");
        jsonData = jsonData.replace("right-eye","rightEye");
        ObjectMapper objectMapper = new ObjectMapper();
        FacialFeatures facialFeatures = objectMapper.readValue(jsonData, FacialFeatures.class);

        return facialFeatures.getLips().getUser_landmark_image();
    }

    public Map<String, LoadApiContentDto> getObject(String jsonData) throws JsonProcessingException {
        jsonData = jsonData.replace("left-eye","leftEye");
        jsonData = jsonData.replace("right-eye","rightEye");
        ObjectMapper objectMapper = new ObjectMapper();
        FacialFeatures facialFeatures = objectMapper.readValue(jsonData, FacialFeatures.class);

        Map<String, LoadApiContentDto> hashMap = new LinkedHashMap<>();

        String[] featureNames = {"leftEye", "rightEye", "nose", "lips"};


        for (String featureName : featureNames) {
            JsonArray feature = null;

            switch (featureName) {
                case "leftEye":
                    feature = facialFeatures.getLeftEye();
                    break;
                case "rightEye":
                    feature = facialFeatures.getRightEye();
                    break;
                case "nose":
                    feature = facialFeatures.getNose();
                    break;
                case "lips":
                    feature = facialFeatures.getLips();
                    break;
                default:
                    break;
            }

            if (feature != null) {
                LoadApiContentDto jsonFeature = new LoadApiContentDto();
                jsonFeature.setWannabeName(feature.getMost_similar_wannabe());
                jsonFeature.setScore(feature.getScore());
                jsonFeature.setImage(feature.getUser_landmark_image());

                hashMap.put(featureName, jsonFeature);
            }
        }
//        LoadApiContentDto jsonLeftEye = new LoadApiContentDto();
//
//        jsonLeftEye.setWannabeName(facialFeatures.getLeftEye().getMost_similar_wannabe());
//        jsonLeftEye.setScore(facialFeatures.getLeftEye().getScore());
//        jsonLeftEye.setImage(facialFeatures.getLeftEye().getUser_landmark_image());
//
//        hashMap.put("leftEye", jsonLeftEye);
//
//        LoadApiContentDto jsonRightEye = new LoadApiContentDto();
//
//        jsonRightEye.setWannabeName(facialFeatures.getRightEye().getMost_similar_wannabe());
//        jsonRightEye.setScore(facialFeatures.getRightEye().getScore());
//        jsonRightEye.setImage(facialFeatures.getRightEye().getUser_landmark_image());
//
//        hashMap.put("rightEye", jsonRightEye);
//
//        LoadApiContentDto jsonNose = new LoadApiContentDto();
//
//        jsonNose.setWannabeName(facialFeatures.getNose().getMost_similar_wannabe());
//        jsonNose.setScore(facialFeatures.getNose().getScore());
//        jsonNose.setImage(facialFeatures.getNose().getUser_landmark_image());
//
//        hashMap.put("nose", jsonNose);
//
//        LoadApiContentDto jsonLips = new LoadApiContentDto();
//
//        jsonLips.setWannabeName(facialFeatures.getLips().getMost_similar_wannabe());
//        jsonLips.setScore(facialFeatures.getLips().getScore());
//        jsonLips.setImage(facialFeatures.getLips().getUser_landmark_image());
//
//        hashMap.put("lips", jsonLips);


        return hashMap;
    }
}
