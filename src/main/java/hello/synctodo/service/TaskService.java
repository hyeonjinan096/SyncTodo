package hello.synctodo.service;

import hello.synctodo.dto.TaskDTO;
import org.springframework.stereotype.Service;


public interface TaskService {

    Long register(TaskDTO taskDTO);

    void delete(Long taskId);

    void modify(Long taskId, TaskDTO taskDTO);

    TaskDTO read(Long taskId);

}
