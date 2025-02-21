package com.example.CDIO.domain;

import java.time.Instant;

import com.example.CDIO.util.constant.ConditionEnum;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    private double price;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    // @Enumerated(EnumType.STRING)
    // private String condition;

    private String imageUrl;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    public void handleBeforeCreateAt() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdateAt() {
        this.updatedAt = Instant.now();
    }
}