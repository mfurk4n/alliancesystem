package com.alliancesystem.alliancesystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alliances")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alliance {
    @Id
    @GeneratedValue
    @UuidGenerator
    @EqualsAndHashCode.Include
    String id;

    @Column(nullable = false, unique = true, length = 25)
    String name;

    Integer emblem;

    String description;

    boolean joinType;

    Integer requiredLevel;

    @Max(50)
    Integer memberCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "leader_id")
    User leader;

    @OneToMany(mappedBy = "alliance", fetch = FetchType.LAZY)
    @Singular
    Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "alliance", fetch = FetchType.LAZY)
    @Singular
    Set<LifeRequest> lifeRequests = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "alliance_blocked_users",
            joinColumns = @JoinColumn(name = "alliance_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> blockedUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "alliance_requests_users",
            joinColumns = @JoinColumn(name = "alliance_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> userRequestsToJoin = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

}
