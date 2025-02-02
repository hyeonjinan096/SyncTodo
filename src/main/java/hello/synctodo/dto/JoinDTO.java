package hello.synctodo.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {

    private String username;
    private String password;
    private String email;
    private String role;
    private String profileImageUrl;

}

