package Neuroflow.project.dto;

import lombok.Data;

@Data
public class LoadApiContentDto {
    private byte[] image;
    private String wannabeName;
    private String simmilarityScore;
}
