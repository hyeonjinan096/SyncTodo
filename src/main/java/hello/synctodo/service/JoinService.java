package hello.synctodo.service;

import hello.synctodo.domain.User;
import hello.synctodo.dto.JoinDTO;
import hello.synctodo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        String profileImageUrl = joinDTO.getProfileImageUrl();

        Boolean isExist = userRepository.existsUserByUsername(username);

        if (isExist) return;

        User new_user = User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .profileImageUrl(profileImageUrl)
                .isDeleted(false)
                .build();

        userRepository.save(new_user);

    }

}
