package com.exercise.prueba.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true)
    @Pattern(
            message = "should follow the format 'example@test.com'",
            regexp = "^(?!\\.)[\\w\\.]+@[a-zA-Z]+\\.[a-z]{2,3}$")
    private String email;
    @NotBlank(message = "Password is mandatory")
    /*@Size(min = 8, max = 12)
    @Pattern(
            message = "should contain an uppercase and a number",
            regexp = "^(?=[a-zA-Z\\d]{8,12}$)(?=[a-z]*)(?=(?:[a-zA-Z]*[\\d]))(?=(?:[a-z\\d]*[A-Z][a-z\\d]*))[a-zA-Z\\d]*$")*/
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "phone_id")
    private List<Phone> phones;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return this.getIsActive();
    }
}
