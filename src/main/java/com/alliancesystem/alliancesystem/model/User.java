package com.alliancesystem.alliancesystem.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    @UuidGenerator
    @EqualsAndHashCode.Include
    String id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    Integer life;

    Integer coin;

    Integer level;

    Integer avatar;

    boolean inAlliance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alliance_id")
    Alliance alliance;

    @Column(name = "last_request_time")
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime lastLifeRequestTime;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.life = 5;
        this.coin = 500;
        this.level = 1;
        this.avatar = 1;
        this.inAlliance = false;
        this.lastLifeRequestTime = LocalDateTime.now().minusHours(3);
        this.createdAt = LocalDateTime.now();
    }

}
