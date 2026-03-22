package com.demo.messanger.controllers;

import com.demo.messanger.models.ChatRoom;
import com.demo.messanger.models.User;
import com.demo.messanger.services.ChatRoomService;
import com.demo.messanger.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;

    public ChatRoomController(ChatRoomService chatRoomService, UserService userService) {
        this.chatRoomService = chatRoomService;
        this.userService = userService;
    }

    @PostMapping
    public ChatRoom createChatRoom(@RequestParam String name, @RequestParam Long adminId) {
        User admin = userService.findById(adminId);
        if (admin == null) {
            throw new RuntimeException("Admin user not found");
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setAdmin(admin);
        return chatRoomService.save(chatRoom);
    }

    @GetMapping("/{id}")
    public ChatRoom getChatRoom(@PathVariable Long id) {
        return chatRoomService.getChatRoomById(id)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
    }

    @GetMapping("/user/{userId}")
    public List<ChatRoom> getUserChatRooms(@PathVariable Long userId) {
        return chatRoomService.getUserChatRooms(userId);
    }

    @PostMapping("/{chatId}/addUser/{userId}")
    public ChatRoom addUserToChat(@PathVariable Long chatId, @PathVariable Long userId) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        chatRoomService.addUserToRoom(chatRoom, user);
        return chatRoom;
    }

    @PostMapping("/{chatId}/removeUser/{userId}")
    public ChatRoom removeUserFromChat(@PathVariable Long chatId, @PathVariable Long userId) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        chatRoomService.removeUserFromRoom(chatRoom, user);
        return chatRoom;
    }
}