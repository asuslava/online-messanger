package com.demo.messanger.services;

import com.demo.messanger.models.User;
import com.demo.messanger.models.ChatRoom;
import com.demo.messanger.repositories.UserRepository;
import com.demo.messanger.repositories.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public UserService(UserRepository userRepository, ChatRoomRepository chatRoomRepository){
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<ChatRoom> getUserChatRooms(Long userId){
        return chatRoomRepository.findAllByUserId(userId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}