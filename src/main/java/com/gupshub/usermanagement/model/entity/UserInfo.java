package com.gupshub.usermanagement.model.entity;

import com.gupshub.usermanagement.util.SupabaseUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = SupabaseUtils.USER_TABLE)
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
