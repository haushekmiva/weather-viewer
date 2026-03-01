package com.haushekmiva.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @NonNull
    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

}
