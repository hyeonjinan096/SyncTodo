package hello.synctodo.service;

import hello.synctodo.domain.User;
import hello.synctodo.dto.JoinDTO;
import hello.synctodo.dto.TaskDTO;
import hello.synctodo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
@Transactional
public class JoinServiceTests {

    @Autowired
    JoinService joinService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testJoin() {
        JoinDTO joinDTO = JoinDTO.builder()
                .username("id")
                .password("pwd")
                .email("email")
                .profileImageUrl("url")
                .build();

        joinService.joinProcess(joinDTO);

        User user = userRepository.findByUsername("id");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("id");
        assertThat(user.getEmail()).isEqualTo("email");
    }

}
