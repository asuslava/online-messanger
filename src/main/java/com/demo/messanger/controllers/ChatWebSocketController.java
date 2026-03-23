package com.demo.messanger.controllers;

import com.demo.messanger.dto.ChatMessageDTO;
import com.demo.messanger.models.ChatRoom;
import com.demo.messanger.models.Message;
import com.demo.messanger.models.User;
import com.demo.messanger.services.ChatRoomService;
import com.demo.messanger.services.MessageService;
import com.demo.messanger.services.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final ChatRoomService chatRoomService;

    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate,
                                   MessageService messageService,
                                   UserService userService,
                                   ChatRoomService chatRoomService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.userService = userService;
        this.chatRoomService = chatRoomService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDTO chatMessage) {

        // 1. Получаем пользователя
        User sender = userService.findById(chatMessage.getSenderId());

        // 2. Получаем чат
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatMessage.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        // 3. Сохраняем сообщение в БД
        Message savedMessage = messageService.sendMessageToRoom(
                sender,
                chatRoom,
                chatMessage.getContent()
        );

        // 4. Отправляем всем подписчикам
        messagingTemplate.convertAndSend(
                "/topic/chat/" + chatRoom.getId(),
                savedMessage
        );
    }
}