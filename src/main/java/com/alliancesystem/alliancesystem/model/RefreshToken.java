package com.alliancesystem.alliancesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue
    @UuidGenerator
    @EqualsAndHashCode.Include
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnore
    User user;

    @Column(unique = true)
    String token;

    @Temporal(TemporalType.TIMESTAMP)
    Date expiryDate;
}
