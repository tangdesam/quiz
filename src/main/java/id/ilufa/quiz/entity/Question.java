package id.ilufa.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_SEQ")
    @SequenceGenerator(name = "QUESTION_SEQ", allocationSize = 1)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Option> optionList;

}
