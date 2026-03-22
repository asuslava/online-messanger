package com.demo.messanger.services;

import com.demo.messanger.models.ChatRoom;
import com.demo.messanger.models.User;
import com.demo.messanger.repositories.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;

    }
    public ChatRoom save(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    public Optional<ChatRoom> getChatRoomById(Long id){
        return chatRoomRepository.findById(id);
    }

    public List<ChatRoom> getUserChatRooms(Long userId) {
        return chatRoomRepository.findAllByUserId(userId);
    }

    public void addUserToRoom(ChatRoom chatRoom, User user) {
        chatRoom.addUser(user);
        chatRoomRepository.save(chatRoom);
    }

    public void removeUserFromRoom(ChatRoom chatRoom, User user) {
        chatRoom.removeUser(user);
        chatRoomRepository.save(chatRoom);
    }

}
