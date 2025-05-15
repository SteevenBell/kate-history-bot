package com.katehistory.kate_history.model;

import com.katehistory.kate_history.model.enums.QuestionType;
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
@Table(name = "test_questions")
public class TestQuestion extends BaseEntity {
    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "weight", nullable = false, columnDefinition = "INT CHECK (weight BETWEEN 1 AND 5)")
    private Integer weight;

    @Column(name = "order_num")
    private Integer orderNum;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;
}
