package com.demo.messanger.services;

import com.demo.messanger.dto.UserDTO;
import com.demo.messanger.dto.response.LoginResponse;
import com.demo.messanger.mappers.UserMapper;
import com.demo.messanger.models.User;
import com.demo.messanger.repositories.UserRepository;
import com.demo.messanger.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil,
                       UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO register(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) throw new RuntimeException("Email exists");
        if (userRepository.findByUsername(username).isPresent()) throw new RuntimeException("Username exists");

        User user = new User(username, "online", email, passwordEncoder.encode(password));
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = jwtUtil.generateToken(user.getUsername(), user.getId());
        return new LoginResponse(token);
    }
}
