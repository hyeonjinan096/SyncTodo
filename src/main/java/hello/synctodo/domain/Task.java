package domain;

import lombok.*;
import org.springframework.util.SimpleIdGenerator;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String title;

    private String description;

    private LocalDate targetDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isCompleted;

    private boolean is_shared;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskUserMapping> taskUserMappings;

//    public void change(String title, String description, LocalDate targetDate,
//                       LocalDateTime startTime, LocalDateTime endTime, List<String> sharedUsers) {
//        this.title = title;
//        this.description = description;
//        this.targetDate = targetDate;
//        this.startTime = startTime;
//        this.endTime = endTime;
//
//        this.taskUserMappings.clear();
//
//        if (sharedUsers != null && !sharedUsers.isEmpty()) {
//            for (String userId : sharedUsers) {
//                boolean isUserAlreadyShared = this.taskUserMappings.stream()
//                        .anyMatch(mapping -> mapping.getUser().getId().equals(userId));
//
//                if (!isUserAlreadyShared) {
//                    User user = userRepository.findById(userId)
//                            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//                    TaskUserMapping taskUserMapping = TaskUserMapping.builder()
//                            .task(this)
//                            .user() //여기 어떻게 하지
//                            .sharedAt(LocalDateTime.now())
//                            .build();
//                    this.taskUserMappings.add(taskUserMapping);
//                }
//            }
//        }
//    }

    public void toggleIsCompleted() {
        isCompleted = !isCompleted;
    }


}
