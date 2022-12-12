package id.ilufa.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_SEQ")
    @SequenceGenerator(name = "QUESTION_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;

    private String text;
}
