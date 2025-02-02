package hello.synctodo.service;

import hello.synctodo.domain.Task;
import hello.synctodo.domain.User;
import hello.synctodo.dto.TaskDTO;
import hello.synctodo.repository.TaskRepository;
import hello.synctodo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
@Transactional
public class TaskServiceTests {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void testCreateTask() {

        TaskDTO taskDTO = TaskDTO.builder()
                .title("title")
                .description("description")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .userId(2L)
                .sharedUsers(List.of(3L))
                .build();

        Long taskId = taskService.register(taskDTO);

        Task savedTask = taskRepository.findById(taskId).orElse(null);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getUser()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo(taskDTO.getTitle());
        assertThat(savedTask.getDescription()).isEqualTo(taskDTO.getDescription());
        assertThat(savedTask.getEndTime()).isEqualTo(taskDTO.getEndTime());
    }

    @Test
    public void testDeleteTask() {
        ModelMapper modelMapper;

        TaskDTO taskDTO = TaskDTO.builder()
                .title("title")
                .description("description")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .userId(2L)
                .sharedUsers(List.of(3L))
                .build();

        Long taskId = taskService.register(taskDTO);

        taskRepository.deleteById(taskId);

        boolean exists = taskRepository.existsById(taskId);
        assertThat(exists).isFalse();

    }

    @Test
    public void testModifyTask() {
        ModelMapper modelMapper;

        TaskDTO taskDTO = TaskDTO.builder()
                .title("title")
                .description("description")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .userId(2L)
                .sharedUsers(List.of(3L))
                .build();

        Long taskId = taskService.register(taskDTO);

        TaskDTO new_taskDTO = TaskDTO.builder()
//                .id(taskId)
                .title("new_title")
                .description("description")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .userId(2L)
                .sharedUsers(List.of(3L))
                .build();

        taskService.modify(taskId, new_taskDTO);

        Task savedTask = taskRepository.findById(taskId).orElse(null);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getUser()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo(new_taskDTO.getTitle());
        assertThat(savedTask.getDescription()).isEqualTo(new_taskDTO.getDescription());
        assertThat(savedTask.getEndTime()).isEqualTo(new_taskDTO.getEndTime());

    }

    @Test
    public void testReadTask() {
        TaskDTO taskDTO = TaskDTO.builder()
                .title("title")
                .description("description")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .userId(2L)
                .sharedUsers(List.of(3L))
                .build();

        Long taskId = taskService.register(taskDTO);

        TaskDTO task = taskService.read(taskId);

        assertThat(task).isNotNull();

    }

}
