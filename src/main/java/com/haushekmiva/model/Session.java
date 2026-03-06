package com.haushekmiva.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Session {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @NonNull
    private UUID id;

    @NonNull
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    private User user;

}
