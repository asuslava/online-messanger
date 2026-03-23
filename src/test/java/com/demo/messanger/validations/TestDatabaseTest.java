package com.demo.messanger.validations;

import com.demo.messanger.models.ChatRoom;
import com.demo.messanger.models.ChatType;
import com.demo.messanger.models.User;
import com.demo.messanger.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional  // откатывает изменения после теста
@Rollback(true)  // явно откат транзакции после теста

@Component
public class TestDatabaseTest implements CommandLineRunner {

    @Autowired
    private com.demo.messanger.repositories.ChatRoomRepository chatRoomRepository;


    @BeforeEach
    void cleanDatabase() {
        chatRoomRepository.deleteAll();
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveChatRoom() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("123");
        userRepository.save(admin);

        ChatRoom room = new ChatRoom("Test Room", ChatType.GROUP, admin);
        chatRoomRepository.save(room);

        assertNotNull(room.getId());
        assertEquals("admin", room.getAdmin().getUsername());
        assertEquals(1, room.getUsers().size());
    }
    @Override
    public void run(String... args) throws Exception {
        // Создадим тестового пользователя (admin)
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("123"); // для теста
        userRepository.save(admin);

        // Создаём чат
        ChatRoom room = new ChatRoom("Test Room", ChatType.GROUP, admin);
        chatRoomRepository.save(room);

        // Проверяем
        System.out.println("ChatRoom saved:");
        System.out.println("ID: " + room.getId());
        System.out.println("Name: " + room.getName());
        System.out.println("Admin: " + room.getAdmin().getUsername());
        System.out.println("Users count: " + room.getUsers().size());
    }


}

