package com.smart.water.plan.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class User {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private int threshold;
    private String zipcode;
    private Double lat;
    private Double lon;
    private String address;

    public User(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.threshold = 60; // default threshold
    }
}