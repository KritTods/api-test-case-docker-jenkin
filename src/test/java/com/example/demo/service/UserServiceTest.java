
package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    public void testGetUserFullNameById() {
        UserRepository mockRepo = mock(UserRepository.class);
        UserService service = new UserService(mockRepo);

        when(mockRepo.findById(1)).thenReturn(new User("John", "Doe"));

        String fullName = service.getUserFullNameById(1);
        assertEquals("John Doe", fullName);
    }
}
