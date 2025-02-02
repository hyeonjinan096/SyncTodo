package hello.synctodo.controller;

import hello.synctodo.domain.FriendStatus;
import hello.synctodo.dto.JoinDTO;
import hello.synctodo.dto.UserDTO;
import hello.synctodo.service.FriendService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@Log4j2
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchFriends(@PathVariable Long userId) {
        log.info("mmmmmm");
        List<UserDTO> users = friendService.searchFriends(userId);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestFriend(@RequestParam Long senderId, @RequestParam Long receiverId) {
        friendService.requestFriend(senderId, receiverId);
        return ResponseEntity.ok("Friend request sent.");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriend(@RequestParam Long senderId, @RequestParam Long receiverId) {
        friendService.acceptFriend(senderId, receiverId);
        return ResponseEntity.ok("Friend request accepted.");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriend(@RequestParam Long senderId, @RequestParam Long receiverId) {
        friendService.rejectFriend(senderId, receiverId);
        return ResponseEntity.ok("Friend request rejected.");
    }

    @PostMapping("/block")
    public ResponseEntity<String> blockFriend(@RequestParam Long senderId, @RequestParam Long receiverId) {
        friendService.blockFriend(senderId, receiverId);
        return ResponseEntity.ok("Friend blocked.");
    }

    @PostMapping("/change/status")
    public ResponseEntity<String> changeFriendStatus(@RequestParam Long senderId, @RequestParam Long receiverId,
                                                     @RequestParam FriendStatus status) {
        friendService.changeFriendStatus(senderId, receiverId, status);
        return ResponseEntity.ok("Friend status updated to " + status);
    }
}
