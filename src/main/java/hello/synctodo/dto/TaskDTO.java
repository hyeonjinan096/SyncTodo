package hello.synctodo.dto;

import hello.synctodo.domain.TaskUserMapping;
import hello.synctodo.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//Validation추가

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

//    private Long id; 지우는게  id = null오류 떠서

    private String title;

    private String description;

    private LocalDate targetDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isCompleted;

    private boolean isShared;

    private Long userId;

    private List<Long> sharedUsers;

}

