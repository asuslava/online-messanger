package com.demo.messanger.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Entity
public class Message {

    private User user;
    private String message;
    private ChatRoom chatRoom;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID;

    private LocalTime messageTime;

    public Message(String message, User user){
        if (!(message.isBlank())) {
            this.message = message;
        }
        messageTime = LocalTime.now();
        this.user = user;
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
