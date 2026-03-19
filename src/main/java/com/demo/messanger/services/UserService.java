package com.demo.messanger.services;

import com.demo.messanger.models.User;
import com.demo.messanger.models.ChatRoom;
import com.demo.messanger.repositories.UserRepository;
import com.demo.messanger.repositories.ChatRoomRepository;
import com.demo.messanger.security.JwtUtil;
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

    // Регистрация
    public User register(String username, String email, String password){
        User user = new User(username, "online", email, password);
        // TODO: hash password (BCrypt)
        return userRepository.save(user);
    }

    // Логин + выдача JWT
    public String login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // TODO: проверить хэш пароля
        String token = JwtUtil.generateToken(user.getUsername());
        user.setToken(token);
        userRepository.save(user);
        return token;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<ChatRoom> getUserChatRooms(Long userId){
        return chatRoomRepository.findAllByUserId(userId);
    }
}