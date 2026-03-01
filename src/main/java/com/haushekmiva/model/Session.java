package com.haushekmiva.model;


import com.mysql.cj.protocol.ColumnDefinition;
import jakarta.persistence.*;
import lombok.*;

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
    @NonNull
    private UUID id;

    @NonNull
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

}
