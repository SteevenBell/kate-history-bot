package com.katehistory.kate_history.model;

import com.katehistory.kate_history.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "telegram_id", nullable = false, unique = true)
    private Long telegramId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
}
