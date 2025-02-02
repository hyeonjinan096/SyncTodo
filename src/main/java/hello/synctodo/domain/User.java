package hello.synctodo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
@ToString
@Getter
@Setter
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String username;
    private String password;
    private String email;
    private String profileImageUrl;
    private String role;
    private boolean isDeleted;

    public void changePassword(String password) { this.password = password;}
    public void changeEmail(String email) { this.email = email;}
    public void changeProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl;}
    public void delete(boolean isDeleted){
        this.isDeleted = isDeleted ? true : null;
    }
}
