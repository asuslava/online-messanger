package com.demo.messanger.controllers;

import com.demo.messanger.dto.UserDTO;
import com.demo.messanger.mappers.UserMapper;
import com.demo.messanger.models.User;
import com.demo.messanger.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return userMapper.toDTO(user);
    }

    @GetMapping("/{id}/chats")
    public List<Long> getUserChatRooms(@PathVariable Long id) {
        return userService.getUserChatRooms(id)
                .stream()
                .map(chatRoom -> chatRoom.getId())
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userService.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (userDTO.getUsername() != null) user.setUsername(userDTO.getUsername());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());

        userService.save(user);
        return userMapper.toDTO(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "User with ID " + id + " deleted successfully";
    }
}