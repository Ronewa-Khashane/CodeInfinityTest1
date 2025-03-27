package com.khashane.codeinfinitytest1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khashane.codeinfinitytest1.domain.User;
import com.khashane.codeinfinitytest1.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        testUser = new User("1234567890123", "John", "Doe", LocalDate.of(1995, 5, 15));
    }

    @Test
    void testCreateUser_Success() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.name").value(testUser.getName()));
    }

    @Test
    void testGetUserById_Found() throws Exception {
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/users/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.name").value(testUser.getName()));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById("0000000000000")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{id}", "0000000000000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllUsers_Success() throws Exception {
        List<User> users = Arrays.asList(testUser, new User("9876543210987", "Jane", "Smith", LocalDate.of(1998, 3, 20)));
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        when(userService.updateUser(eq(testUser.getId()), any(User.class))).thenReturn(Optional.of(testUser));

        mockMvc.perform(put("/api/users/{id}", testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.name").value(testUser.getName()));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        when(userService.updateUser(eq("0000000000000"), any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/users/{id}", "0000000000000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        when(userService.deleteUser(testUser.getId())).thenReturn(true);

        mockMvc.perform(delete("/api/users/{id}", testUser.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        when(userService.deleteUser("0000000000000")).thenReturn(false);

        mockMvc.perform(delete("/api/users/{id}", "0000000000000"))
                .andExpect(status().isNotFound());
    }
}