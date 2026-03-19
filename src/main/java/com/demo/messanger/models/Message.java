package com.demo.messanger.models;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "messages")
public class Message {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String message;
    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID;

    private LocalTime messageTime;

    public Message(){}

    public Message(String message, User user ,ChatRoom chatRoom){
        if (!(message.isBlank())) {
            this.message = message;
        }
        else {
            throw new IllegalArgumentException("Message cannot be blank");
        }
        this.user = user;
        this.chatRoom = chatRoom;
    }
    @PrePersist
    protected void onCreate() {
        this.messageTime = LocalTime.now();
    }



    public String getMessage(){                         //Getters
        return message;
    }

    public User getUser(){
        return user;
    }
    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setUser(User user){
        this.user = user;
    }






}
