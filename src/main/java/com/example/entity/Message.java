package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "FirstName cannot be null")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "LastName cannot be null")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Column(nullable = false)
    private String email;

    @NotNull(message = "Mobile Number cannot be null")
    @Column(nullable = false)
    private String mobileno;

    @NotNull(message = "Message cannot be null")
    @Column(nullable = false)
    private String message;

    @Version
    @Column(name = "version")
    private Integer version; // For optimistic locking

    // Default constructor
    public Message() {}

    // Constructor with all fields
    public Message(Long id, String firstName, String lastName, String email, String mobileno, String message) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileno = mobileno;
        this.message = message;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", mobileno=" + mobileno + ", message=" + message + ", version=" + version + "]";
    }
}
