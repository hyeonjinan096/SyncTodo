package hello.synctodo.domain;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId1;
    private Long userId2;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

}

