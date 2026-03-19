package com.demo.messanger.services;

import com.demo.messanger.models.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {
    private final com.demo.messanger.repositories.ChatRoomRepository chatRoomRepository;
    public ChatRoomService(com.demo.messanger.repositories.ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;

    }
    public ChatRoom save(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom getChatRoomById(long id){
        return chatRoomRepository.findById(id);
    }
}
