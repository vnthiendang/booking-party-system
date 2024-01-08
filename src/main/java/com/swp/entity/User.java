package com.swp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "app_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")})
@Getter
@Setter
public class User implements UserDetails {
    public User(Integer usId) {
        this.usId = usId;
    }
    public Integer getUsId() {
        return usId;
    }
    public User(String name) {
        this.display_name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer usId;

    @Column(name = "display_name", nullable = false)
    private String display_name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    private Roles role_id;

    @Column(name = "created_date")
    private LocalDateTime created_date;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role_id.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(usId, user.usId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usId);
    }
}
