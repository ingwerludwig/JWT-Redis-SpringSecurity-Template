package com.javagrind.oauth2practice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NonNull
    @Column(name ="role_id", nullable = false)
    private String role_id;

    @Enumerated(EnumType.STRING)
    private Role role;

    public RolesEntity(Role role){
        this.setRole(role);
    }
}
