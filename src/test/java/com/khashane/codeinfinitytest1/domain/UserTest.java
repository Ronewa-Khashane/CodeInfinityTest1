package com.khashane.codeinfinitytest1.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class UserTest {

    @Test
    void testUserCreation_Success() {
        User user = new User("1234567890123", "John", "Doe", LocalDate.of(1990, 1, 1));

        assertNotNull(user);
        assertEquals("1234567890123", user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals(LocalDate.of(1990, 1, 1), user.getDateOfBirth());
    }

    @Test
    void testEquals_HashCode() {
        User user1 = new User("1234567890123", "John", "Doe", LocalDate.of(1990, 1, 1));
        User user2 = new User("1234567890123", "John", "Doe", LocalDate.of(1990, 1, 1));

        assertEquals(user1, user2); // ✅ Objects with same data should be equal
        assertEquals(user1.hashCode(), user2.hashCode()); // ✅ HashCodes should match
    }

    @Test
    void testToString() {
        User user = new User("1234567890123", "John", "Doe", LocalDate.of(1990, 1, 1));

        String expectedString = "User{id='1234567890123', name='John', surname='Doe', dateOfBirth=1990-01-01}";
        assertEquals(expectedString, user.toString()); // ✅ toString() output should match
    }

    @Test
    void testInvalidIdNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new User("12345", "John", "Doe", LocalDate.of(1990, 1, 1)); // ❌ Invalid ID (not 13 digits)
        });

        assertTrue(exception.getMessage().contains("ID Number must be exactly 13 digits."));
    }

    @Test
    void testInvalidName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new User("1234567890123", "John123", "Doe", LocalDate.of(1990, 1, 1)); // ❌ Invalid name (contains numbers)
        });

        assertTrue(exception.getMessage().contains("Name must contain only letters."));
    }

    @Test
    void testInvalidDateOfBirth_FutureDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new User("1234567890123", "John", "Doe", LocalDate.of(2050, 1, 1)); // ❌ Future date of birth
        });

        assertTrue(exception.getMessage().contains("Date of Birth must be in the past."));
    }
}