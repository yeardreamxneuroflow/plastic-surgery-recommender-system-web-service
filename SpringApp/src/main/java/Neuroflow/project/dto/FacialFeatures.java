package Neuroflow.project.dto;

import lombok.Data;

@Data
public class FacialFeatures {
    private JsonArray leftEye;
    private JsonArray rightEye;
    private JsonArray nose;
    private JsonArray lips;
}
