package com.javagrind.oauth2practice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NonNull
    @Column(name ="role_id", nullable = false)
    private Integer role_id;

    @Enumerated(EnumType.STRING)
    private Role roleName;

    public RolesEntity(Role role){
        this.setRoleName(role);
    }
}
