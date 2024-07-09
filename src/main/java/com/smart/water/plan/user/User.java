package com.smart.water.plan.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private int threshold;
    private String zipcode;
    private Double lat;
    private Double lon;
    private String address;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    public User(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.threshold = 60; // default threshold
    }
}