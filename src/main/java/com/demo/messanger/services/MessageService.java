package com.demo.messanger.service;

import com.demo.messanger.models.Message;
import com.demo.messanger.models.User;
import com.demo.messanger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public ArrayList<Message> findMessagesByUserID(Long userID){
        return messageRepository.findMessagesByUserID(userID);
    }

    public ArrayList<Message> findMessagesByRoomID(Long roomID){
        return messageRepository.findMessagesByRoomID(roomID);
    }

    public void deleteMessageByUSerID(Long userID){
        messageRepository.deleteMessagesByUserID(userID);
    }

    public ArrayList<User> findUSerByRoomID(Long roomID){
        return messageRepository.findUsersByRoomID(roomID);
    }

    public User findUserByMessageID(Long messageID){
        return messageRepository.findUserByMessageID(messageID);
    }

    public void sendMessage(User user, String message){

    }

    public void sendMessageToRoom(Long senderID, Long roomID, String message){

    }


}
