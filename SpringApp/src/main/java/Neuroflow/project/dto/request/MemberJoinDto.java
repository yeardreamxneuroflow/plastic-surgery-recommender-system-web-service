package Neuroflow.project.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberJoinDto {

    private String id;
    private String username;
    private String password;
    private LocalDateTime created_at;
    private String role ;

    public MemberJoinDto(String id, String username, String password, LocalDateTime created_at) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.created_at = created_at;
        this.role = "ROLE_USER";
    }

}
