package com.katehistory.kate_history.model;

import com.katehistory.kate_history.model.enums.AnswerType;
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
@Table(name = "test_answers")
public class TestAnswer extends BaseEntity {
    @Column(name = "answer_value", nullable = false)
    private String answerValue; // текст правильного ответа

    @Column(name = "explanation")
    private String explanation; // объяснение к ответу

    @Enumerated(EnumType.STRING)
    @Column(name = "answer_type")
    private AnswerType answerType; // тип ответа: exact / keywords

    @Column(name = "is_case_sensitive")
    private Boolean isCaseSensitive = false; // учитывать регистр?

    @Column(name = "is_correct")
    private Boolean isCorrect = false; // является ли этот ответ правильным

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private TestQuestion question; // связь с вопросом
}
