package Neuroflow.project.service;

import Neuroflow.project.dto.ImageContentDto;
import Neuroflow.project.dto.LoadApiContentDto;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static io.netty.util.internal.PlatformDependent.getObject;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    private final NamingByTimeService namingByTimeService;

    public S3Service(AmazonS3 amazonS3, NamingByTimeService namingByTimeService) {
        this.amazonS3 = amazonS3;
        this.namingByTimeService = namingByTimeService;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String client_bucket;

    @Value("${cloud.aws.s3.wannabe-bucket}")
    private String wannabe_bucket;

    @Value("${cloud.aws.s3.wannabe-landmark-bucket}")
    private String wannabe_landmark_bucket;

    public ImageContentDto insertImage(MultipartFile multipartFile, String username) throws IOException {
        String originalFilename = namingByTimeService.namingByNowTime(username);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(client_bucket, originalFilename, multipartFile.getInputStream(), metadata);

        return new ImageContentDto(originalFilename, amazonS3.getUrl(client_bucket, originalFilename).toString());
    }

    public byte[] getWannabeImage(String wannabeName) throws IOException {

        String originalFilename = wannabeName + "/original.jpg";

        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(wannabe_bucket, originalFilename));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        return IOUtils.toByteArray(objectInputStream);
    }

    public String getWannabeLandmarkImage(String wannabeName, String landmarkLoc) throws IOException {

        String landmark = null;

        switch (landmarkLoc){
            case "leftEye":
                landmark = "left-eye";

                break;

            case "rightEye":
                landmark = "right-eye";

                break;

            case "nose":
                landmark = "nose";

                break;

            case "lips":
                landmark = "lips";

                break;
        }

        String originalFilename = wannabeName + "/" + landmark + ".jpg";

        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(wannabe_landmark_bucket, originalFilename));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        return Base64.getEncoder().encodeToString(IOUtils.toByteArray(objectInputStream));
    }

    public String maxSimilarityAndGetImage(Map<String, LoadApiContentDto> JsonObject) throws IOException {

        Map<String, Double> values = new HashMap<>();
        values.put("leftEye", Double.valueOf(JsonObject.get("leftEye").getScore()));
        values.put("rightEye", Double.valueOf(JsonObject.get("rightEye").getScore()));
        values.put("nose", Double.valueOf(JsonObject.get("nose").getScore()));
        values.put("lips", Double.valueOf(JsonObject.get("lips").getScore()));

// 최댓값 찾기
        Double maxSimilarity = Collections.max(values.values());

// 최댓값과 연관된 키 찾기
        String keyWithMaxValue = values.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), maxSimilarity))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        System.out.println("Max value: " + maxSimilarity);
        System.out.println("Key with max value: " + keyWithMaxValue);

        String originalFilename = JsonObject.get(keyWithMaxValue).getWannabeName() + "/original.jpg";

        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(wannabe_bucket, originalFilename));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        return Base64.getEncoder().encodeToString(IOUtils.toByteArray(objectInputStream));
    }

    public String getOutputFileUrl(String wannabeName){ return ""; }

    public String getFileKey(){
        return "";
    }
}
