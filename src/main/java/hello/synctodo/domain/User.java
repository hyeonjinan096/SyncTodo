package domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String password;
    private String email;
    private String profileImageUrl;
    private boolean isDeleted;

    public void changePassword(String password) { this.password = password;}
    public void changeEmail(String email) { this.email = email;}
    public void changeProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl;}
    public void delete(boolean isDeleted){
        this.isDeleted = isDeleted ? true : null;
    }
}
