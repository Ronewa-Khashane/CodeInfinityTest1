package com.khashane.codeinfinitytest1.repository;

import com.khashane.codeinfinitytest1.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest  // ✅ Initializes an in-memory MongoDB instance for testing
//@TestPropertySource(properties = "spring.mongodb.embedded.version=4.0.2") // ✅ Specify Embedded MongoDB Version
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("1234567890123", "John", "Doe", LocalDate.of(1995, 5, 15));
        userRepository.save(testUser);  // ✅ Save a test user before each test
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // ✅ Clean database after each test
    }

    @Test
    void testSaveUser_Success() {
        User user = new User("9876543210987", "Jane", "Smith", LocalDate.of(1998, 3, 20));
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo("9876543210987");
    }

    @Test
    void testFindById_UserExists() {
        Optional<User> foundUser = userRepository.findById(testUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John");
    }

    @Test
    void testFindById_UserNotFound() {
        Optional<User> foundUser = userRepository.findById("0000000000000");

        assertThat(foundUser).isEmpty();
    }

    @Test
    void testFindAll_UsersExist() {
        List<User> users = userRepository.findAll();

        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    void testDeleteUser_Success() {
        userRepository.deleteById(testUser.getId());

        Optional<User> deletedUser = userRepository.findById(testUser.getId());

        assertThat(deletedUser).isEmpty();
    }
}