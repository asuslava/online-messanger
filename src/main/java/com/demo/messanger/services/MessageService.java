package com.demo.messanger.services;

import com.demo.messanger.models.ChatRoom;
import com.demo.messanger.models.Message;
import com.demo.messanger.models.User;
import com.demo.messanger.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> findMessagesByUserID(Long userID){
        return messageRepository.findMessagesByUserID(userID);
    }

    public List<Message> findMessagesByRoomID(Long roomID){
        return messageRepository.findMessagesByRoomID(roomID);
    }

    public void deleteMessageByUSerID(Long userID){
        messageRepository.deleteMessagesByUserID(userID);
    }

    public List<User> findUserByRoomID(Long roomID){
        return messageRepository.findUsersByRoomID(roomID);
    }

    public User findUserByMessageID(Long messageID){
        return messageRepository.findUserByMessageID(messageID);
    }

    public Message sendMessageToRoom(User sender, ChatRoom chatRoom, String content){
        if(!chatRoom.getUsers().contains(sender)){
            throw new RuntimeException("User not in chat room");
        }
        Message message = new Message(content, sender,chatRoom);
        message.setChatRoom(chatRoom);
        return messageRepository.save(message);
    }


}
