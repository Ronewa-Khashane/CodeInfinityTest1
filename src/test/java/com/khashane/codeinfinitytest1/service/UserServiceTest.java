package com.khashane.codeinfinitytest1.service;

import com.khashane.codeinfinitytest1.domain.User;
import com.khashane.codeinfinitytest1.exception.DuplicateIdException;
import com.khashane.codeinfinitytest1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User("1234567890123", "John", "Doe", LocalDate.of(1990, 1, 1));
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.existsById(testUser.getId())).thenReturn(false);
        when(userRepository.save(testUser)).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals("1234567890123", createdUser.getId());

        verify(userRepository, times(1)).save(testUser); // ✅ Correct verify() usage
    }

    @Test
    void testCreateUser_DuplicateId() {
        when(userRepository.existsById(testUser.getId())).thenReturn(true);

        assertThrows(DuplicateIdException.class, () -> userService.createUser(testUser));
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById("1234567890123")).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userService.getUserById("1234567890123");

        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getName());

        verify(userRepository, times(1)).findById("1234567890123"); // ✅ Correct verify() usage
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        List<User> users = userService.getAllUsers();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());

        verify(userRepository, times(1)).findAll(); // ✅ Correct verify() usage
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById("1234567890123")).thenReturn(true);
        doNothing().when(userRepository).deleteById("1234567890123");

        boolean deleted = userService.deleteUser("1234567890123");

        assertTrue(deleted);
        verify(userRepository, times(1)).deleteById("1234567890123"); // ✅ Correct verify() usage
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById("1234567890123")).thenReturn(false);

        boolean deleted = userService.deleteUser("1234567890123");

        assertFalse(deleted);
        verify(userRepository, times(1)).existsById("1234567890123"); // ✅ Correct verify() usage
    }
}