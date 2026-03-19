package com.demo.messanger.models;

import com.demo.messanger.validations.UserValidator;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;

    // CONSTRUCTORS
    // ==========================================
    public User(String username, String status, String email, String password) {
        this.status = status;
        this.username = username;

        //check if email is valid
        if(!UserValidator.validEmail(email)){
            throw new IllegalArgumentException("Invalid email");
        } else {
            this.email = email;
        }

        //checks if password is valid
        if(!UserValidator.validPassword(password)){
            throw new IllegalArgumentException("Invalid password");
        } else {
            this.password = password;
        }
    }
    public User(){}
    // ==========================================


    // GETTERS AND SETTERS
    // ==========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getStatus() { return status; }
    public String getEmail() { return email; }
    public void setStatus(String status) { this.status = status; }
    // ==========================================

    @ManyToMany(mappedBy = "users")
    private List<ChatRoom> chatRooms = new ArrayList<>();
    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }
    public void setChatRooms(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }
    public void addChatRoom(ChatRoom chatRoom){
        this.chatRooms.add(chatRoom);
    }
    public void removeChatRoom(ChatRoom chatRoom){
        this.chatRooms.remove(chatRoom);
    }

}