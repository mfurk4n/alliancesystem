package com.alliancesystem.alliancesystem.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "gifted_lifes")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftedLife {
    @Id
    @GeneratedValue
    @UuidGenerator
    @EqualsAndHashCode.Include
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    User receiver;

    boolean used;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

    public GiftedLife(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.used = false;
        this.createdAt = LocalDateTime.now();
    }
}
