package com.exercise.prueba.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

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
    @Size(min = 8, max = 12)
    @Pattern(
            message = "should contain an uppercase and a number",
            regexp = "^(?=[a-zA-Z\\d]{8,12}$)(?=[a-z]*)(?=(?:[a-zA-Z]*[\\d]))(?=(?:[a-z\\d]*[A-Z][a-z\\d]*))[a-zA-Z\\d]*$")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "phone_id")
    private List<Phone> phones;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive = true;
}
