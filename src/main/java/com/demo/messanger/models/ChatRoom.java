package com.demo.messanger.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private ChatType type;

    public ChatRoom() {}

    public ChatRoom(String name, ChatType type,User admin) {
        this.name = name;
        this.type = type;
        this.admin = admin;

    }
    // Automatically give time
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (admin != null) {
            users.add(admin);
        }
    }

    // Admin of room
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id",nullable = false)
    private User admin;

    // Members of room
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chat_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ChatType getType() {
        return this.type;
    }
    public void setType(ChatType type) {
        this.type = type;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public User getAdmin() {
        return this.admin;
    }
    public void setAdmin(User admin) {
        this.admin = admin;
    }
    public List<User> getUsers() {
        return this.users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }


}