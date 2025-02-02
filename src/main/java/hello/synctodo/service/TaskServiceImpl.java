package hello.synctodo.service;

import hello.synctodo.domain.*;
import hello.synctodo.dto.TaskDTO;
import hello.synctodo.repository.FriendRepostory;
import hello.synctodo.repository.TaskRepository;
import hello.synctodo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class TaskServiceImpl implements TaskService {

    private final ModelMapper modelMapper;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;
    private final FriendRepostory friendRepostory;

    @Override
    public Long register(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        Long userId = taskDTO.getUserId();

        task.setUser(findUserByUserId(userId));

        task.setTaskUserMappings(createTaskUserMappings(userId, taskDTO.getSharedUsers(),task));

        log.info(task.toString());

        return taskRepository.save(task).getId();
    }

    @Override
    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void modify(Long TaskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(TaskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + TaskId));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setTargetDate(taskDTO.getTargetDate());
        task.setStartTime(taskDTO.getStartTime());
        task.setEndTime(taskDTO.getEndTime());
        task.setTaskUserMappings(createTaskUserMappings(taskDTO.getUserId(), taskDTO.getSharedUsers(), task));

        taskRepository.save(task);

    }

    @Override
    public TaskDTO read(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        return modelMapper.map(task, TaskDTO.class);
    }


    private User findUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    private List<TaskUserMapping> createTaskUserMappings(Long userId, List<Long> sharedUserIds,  Task task) {
        List<TaskUserMapping> taskUserMappings = new ArrayList<>();

        if (sharedUserIds == null || sharedUserIds.isEmpty()) {
            return taskUserMappings;
        }

        for (Long sharedUserId : sharedUserIds) {
            Optional<Friend> friendOptional = friendRepostory.findByUserId1AndUserId2(userId, sharedUserId);

            if (friendOptional.isEmpty() || friendOptional.get().getStatus() != FriendStatus.ACCEPTED) {
                throw new IllegalArgumentException("You are not friends or the friend status is not accepted.");
            }

            User sharedUser = findUserByUserId(sharedUserId);

            TaskUserMapping taskUserMapping = TaskUserMapping.builder()
                    .task(task)
                    .user(sharedUser)
                    .sharedAt(LocalDateTime.now())
                    .build();

            taskUserMappings.add(taskUserMapping);
        }

        return taskUserMappings;
    }

}
