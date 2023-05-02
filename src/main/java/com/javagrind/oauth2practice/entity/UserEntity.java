package com.javagrind.oauth2practice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false)
    private String id;

    @Column(name = "oauth_strategy")
    private String oauth_str;

    @Column(name = "email")
    @NotEmpty(message = "Email is mandatory")
    private String email;

    @Column(name="password")
    @NotEmpty(message = "Password is mandatory")
    private String password;

    @Column(name="username")
    @NotEmpty(message = "Username is mandatory")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "phone_number")
    private String phone_num;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable( name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<RolesEntity> roles = new HashSet<>();

    public Set<RolesEntity> getRoles() {
        return roles;
    }
    public void setRoles(Set<RolesEntity> roles) {
        this.roles = roles;
    }

    public UserEntity(String email, String password, String username){
        this.setEmail(email);
        this.setPassword(password);
        this.setUsername(username);
    }
}
