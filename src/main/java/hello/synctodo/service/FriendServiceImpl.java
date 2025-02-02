package hello.synctodo.service;

import hello.synctodo.domain.Friend;
import hello.synctodo.domain.FriendStatus;
import hello.synctodo.domain.User;
import hello.synctodo.dto.UserDTO;
import hello.synctodo.repository.FriendRepostory;
import hello.synctodo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class FriendServiceImpl implements FriendService{

    private final ModelMapper modelMapper;

    private final FriendRepostory friendRepository;

    private final UserRepository userRepository;
    
    @Override
    public List<UserDTO> searchFriends(Long userId) {

        List<Long> friendIds = friendRepository.findFriendIdsByUserIdWithStatus(userId,FriendStatus.ACCEPTED);

        List<UserDTO> userDTOs = new ArrayList<>();

        for (Long friendId : friendIds) {

            User friend = userRepository.findById(friendId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserDTO userDTO = modelMapper.map(friend, UserDTO.class);
            userDTOs.add(userDTO);

        }

        return userDTOs;
    }

    @Override
    public void requestFriend(Long senderId, Long receiverId) {
        Optional<Friend> existingFriend = friendRepository.findByUserId1AndUserId2(senderId, receiverId);

        //요청 알림 추가
        if (existingFriend.isEmpty()) {
            Friend newFriend = Friend.builder()
                    .userId1(senderId)
                    .userId2(receiverId)
                    .status(FriendStatus.PENDING)
                    .build();
            friendRepository.save(newFriend);
        } else {
            throw new RuntimeException("Friend request already exists or has been processed.");
        }
    }

    @Override
    public void acceptFriend(Long senderId, Long receiverId) {

        Optional<Friend> friendOptional = friendRepository.findByUserId1AndUserId2(receiverId, senderId);

        if (friendOptional.isPresent() && friendOptional.get().getStatus() == FriendStatus.PENDING) {
            Friend friend = friendOptional.get();

            friend.setStatus(FriendStatus.ACCEPTED);
            friendRepository.save(friend);
        } else {
            throw new RuntimeException("No pending friend request to accept.");
        }
    }

    @Override
    public void rejectFriend(Long senderId, Long receiverId) {
        Optional<Friend> friendOptional = friendRepository.findByUserId1AndUserId2(receiverId, senderId);

        if (friendOptional.isPresent() && friendOptional.get().getStatus() == FriendStatus.PENDING) {
            Friend friend = friendOptional.get();

            friend.setStatus(FriendStatus.REJECTED);
            friendRepository.save(friend);
        } else {
            throw new RuntimeException("No pending friend request to reject.");
        }
    }

    @Override
    public void blockFriend(Long senderId, Long receiverId) {
        Optional<Friend> friendOptional = friendRepository.findByUserId1AndUserId2(senderId, receiverId);

        if (friendOptional.isPresent()) {
            Friend friend = friendOptional.get();

            friend.setStatus(FriendStatus.BLOCKED);
            friendRepository.save(friend);
        } else {
            throw new RuntimeException("Friendship not found to block.");
        }
    }

    @Override
    public void changeFriendStatus(Long senderId, Long receiverId, FriendStatus status) {
        Optional<Friend> friendOptional = friendRepository.findByUserId1AndUserId2(senderId, receiverId);

        if (friendOptional.isPresent()) {
            Friend friend = friendOptional.get();

            friend.setStatus(status);
            friendRepository.save(friend);
        } else {
            throw new RuntimeException("Friendship not found to change status.");
        }
    }

}
