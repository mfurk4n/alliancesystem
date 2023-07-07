package com.alliancesystem.alliancesystem.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "life_requests")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LifeRequest {
    @Id
    @GeneratedValue
    @UuidGenerator
    @EqualsAndHashCode.Include
    String id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "life_request_senders",
            joinColumns = @JoinColumn(name = "life_request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> senders = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alliance_id")
    Alliance alliance;

    boolean active;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

    public LifeRequest(User receiver, Alliance alliance) {
        this.receiver = receiver;
        this.alliance = alliance;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

}
