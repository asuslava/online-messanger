package com.demo.messanger.repositories;

import com.demo.messanger.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    //Find chat by ID
    ChatRoom findById(long id);

    @Query("SELECT c FROM ChatRoom c JOIN c.users u WHERE u.id = :userId")
    List<ChatRoom> findAllByUserId(@Param("userId") Long userId);


}