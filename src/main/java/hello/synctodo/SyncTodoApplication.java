package hello.synctodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SyncTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncTodoApplication.class, args);
    }

}
