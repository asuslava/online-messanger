package com.demo.messanger.repositories;

import com.demo.messanger.models.Message;
import com.demo.messanger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesByUserID(Long userID);

    List<Message> findMessagesByRoomID(Long roomID);

    void deleteMessagesByUserID(Long userID);

    @Query("SELECT DISTINCT m.user FROM Message m WHERE m.chatRoom.id = :roomId")
    List<User> findUsersByRoomID(@Param("roomId") Long roomID);

    @Query("SELECT m.user FROM Message m WHERE m.id = :messageId")
    User findUserByMessageID(@Param("messageId") Long messageID);
}