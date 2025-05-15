package com.katehistory.kate_history.model;

import com.katehistory.kate_history.model.enums.SubjectType;
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
@Table(name = "tests")
public class Test extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private SubjectType subject;

    @Column(name = "description")
    private String description;

    @Column(name = "is_published")
    private Boolean isPublished = false;
}
