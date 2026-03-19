package com.demo.messanger.repository;

import com.demo.messanger.models.Message;
import com.demo.messanger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MessageRepository extends JpaRepository<Message, Long> {

    ArrayList<Message> findMessagesByUserID(Long userID);
    ArrayList<Message> findMessagesByRoomID(Long roomID);
    void deleteMessagesByUserID(Long userID);
    ArrayList<User> findUsersByRoomID(Long roomID);
    User findUserByMessageID(Long messageID);
    void sendMessage(User user, String message);


}