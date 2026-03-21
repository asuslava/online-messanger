package com.demo.messanger.services;

import com.demo.messanger.dto.UserDTO;
import com.demo.messanger.mappers.UserMapper;
import com.demo.messanger.models.User;
import com.demo.messanger.repositories.UserRepository;
import com.demo.messanger.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO register(String username, String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User with this username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        userRepository.save(user);

        UserDTO dto = userMapper.toDTO(user);
        return dto;
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String hashedPassword = user.getPassword();
        boolean matches = passwordEncoder.matches(password, hashedPassword);
        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getId());

        return token;

    }

}
