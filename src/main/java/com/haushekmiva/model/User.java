package com.haushekmiva.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"password", "locations", "sessions"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", unique = true, nullable = false, length = 32)
    @NonNull
    private String login;

    @Column(name="password", nullable = false, length = 255)
    @NonNull
    private String password;

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<Location> locations = new ArrayList<>();

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<Session> sessions = new ArrayList<>();
}
