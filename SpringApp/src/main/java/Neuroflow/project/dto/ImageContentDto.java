package Neuroflow.project.dto;

import lombok.Data;

@Data
public class ImageContentDto {
    private String fileKey;

    private String url;

    public ImageContentDto(String fileKey, String url) {
        this.fileKey = fileKey;
        this.url = url;
    }
}
