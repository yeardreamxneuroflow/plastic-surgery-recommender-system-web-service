package Neuroflow.project.service;

import Neuroflow.project.dto.ImageContentDto;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    private String wannabe_bucket = "dev-team12-wannabe-images";


    public ImageContentDto insertImage(MultipartFile multipartFile, String username) throws IOException {
        String originalFilename = namingByTimeService.namingByNowTime(username);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(client_bucket, originalFilename, multipartFile.getInputStream(), metadata);

        return new ImageContentDto(originalFilename, amazonS3.getUrl(client_bucket, originalFilename).toString());
    }

    public String getOutputFileUrl(String wannabeName){ return ""; }

    public String getFileKey(){
        return "";
    }
}
