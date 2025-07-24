package com.katehistory.model;

import com.katehistory.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "telegram_id"))
public class User extends BaseEntity {
    @Column(name = "telegram_id", nullable = false, unique = true)
    private Long telegramId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "username")
    private String username;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
}
