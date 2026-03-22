package com.demo.messanger.controllers;

import com.demo.messanger.models.ChatRoom;
import com.demo.messanger.models.Message;
import com.demo.messanger.models.User;
import com.demo.messanger.services.ChatRoomService;
import com.demo.messanger.services.MessageService;
import com.demo.messanger.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ChatRoomService chatRoomService;

    public MessageController(MessageService messageService, UserService userService, ChatRoomService chatRoomService) {
        this.messageService = messageService;
        this.userService = userService;
        this.chatRoomService = chatRoomService;
    }

    @PostMapping("/send")
    public Message sendMessage(@RequestParam Long userId, @RequestParam Long chatRoomId, @RequestParam String content) {
        User sender = userService.findById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        return messageService.sendMessageToRoom(sender, chatRoom, content);
    }

    @GetMapping("/user/{userId}")
    public List<Message> getMessagesByUser(@PathVariable Long userId) {
        return messageService.findMessagesByUserID(userId);
    }

    @GetMapping("/chat/{chatRoomId}")
    public List<Message> getMessagesByChatRoom(@PathVariable Long chatRoomId) {
        return messageService.findMessagesByRoomID(chatRoomId);
    }

    @GetMapping("/chat/{chatRoomId}/users")
    public List<User> getUsersByChatRoom(@PathVariable Long chatRoomId) {
        return messageService.findUsersByRoomID(chatRoomId);
    }

    @DeleteMapping("/{messageId}")
    public String deleteMessage(@PathVariable Long messageId) {
        Message message = messageService.findUserByMessageID(messageId) != null
                ? messageService.sendMessageToRoom(null, null, "")
                : null;
        messageService.deleteMessagesByUserID(messageId);
        return "Message deleted";
    }
}