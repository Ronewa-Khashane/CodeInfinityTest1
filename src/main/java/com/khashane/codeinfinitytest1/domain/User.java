package com.khashane.codeinfinitytest1.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "users")
public class User {

    @Id
    @Indexed(unique = true) // ✅ Enforces uniqueness in MongoDB
    @Pattern(regexp = "\\d{13}", message = "ID Number must be exactly 13 digits.")
    private String id;

    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters.")
    private String name;

    @NotBlank(message = "Surname cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Surname must contain only letters.")
    private String surname;

    @Past(message = "Date of Birth must be a valid past date.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // ✅ Ensures correct JSON format
    @JsonSerialize(using = LocalDateSerializer.class) // ✅ Serializes LocalDate correctly
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    // ✅ Default Constructor (Required by MongoDB)
    public User() {
    }

    // ✅ Constructor
    public User(String id, String name, String surname, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    // ✅ Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    // ✅ Setters (MongoDB handles validation, so we don't duplicate it here)
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // ✅ Equals & HashCode (For Object Comparisons)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(dateOfBirth, user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, dateOfBirth);
    }

    // ✅ toString() Method (For Debugging)
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}