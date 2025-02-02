package hello.synctodo.domain;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate targetDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isCompleted;

    private boolean is_shared;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<TaskUserMapping> taskUserMappings;

    public void toggleIsCompleted() {
        isCompleted = !isCompleted;
    }


}
