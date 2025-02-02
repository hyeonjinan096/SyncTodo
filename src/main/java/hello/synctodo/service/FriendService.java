package hello.synctodo.service;


import hello.synctodo.domain.FriendStatus;
import hello.synctodo.dto.UserDTO;

import java.util.List;

public interface FriendService {

    List<UserDTO> searchFriends(Long userId);

    void requestFriend(Long senderId, Long receiverId);

    void acceptFriend(Long senderId, Long receiverId);

    void rejectFriend(Long senderId, Long receiverId);

    void blockFriend(Long senderId, Long receiverId);

    void changeFriendStatus(Long senderId, Long receiverId, FriendStatus status);
}
