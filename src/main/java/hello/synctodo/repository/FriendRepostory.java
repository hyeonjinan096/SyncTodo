package hello.synctodo.repository;

import hello.synctodo.domain.Friend;
import hello.synctodo.domain.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepostory extends JpaRepository<Friend, Long> {

    @Query("SELECT f.userId1 FROM Friend f WHERE f.userId2 = :userId AND f.status = :status " +
            "UNION " +
            "SELECT f.userId2 FROM Friend f WHERE f.userId1 = :userId AND f.status = :status")
    List<Long> findFriendIdsByUserIdWithStatus(Long userId, FriendStatus status);

    @Query("SELECT f FROM Friend f WHERE (f.userId1 = :userId1 AND f.userId2 = :userId2) OR (f.userId1 = :userId2 AND f.userId2 = :userId1)")
    Optional<Friend> findByUserId1AndUserId2(Long userId1, Long userId2);
}
