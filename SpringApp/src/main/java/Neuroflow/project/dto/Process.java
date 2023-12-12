package Neuroflow.project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Process {

    private String id;
    private String member_id;
    private LocalDateTime logging_at;


    private String image_input_url;
    private String image_input_file_key;
    private String image_output_url;
    private String image_output_file_key;

    public Process(String id, String member_id, LocalDateTime logging_at, String image_input_url, String image_input_file_key, String image_output_url, String image_output_file_key) {
        this.id = id;
        this.member_id = member_id;
        this.logging_at = logging_at;
        this.image_input_url = image_input_url;
        this.image_input_file_key = image_input_file_key;
        this.image_output_url = image_output_url;
        this.image_output_file_key = image_output_file_key;
    }
}
