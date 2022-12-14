package id.ilufa.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "options") // option is a keyword in SQL
public class Option {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String text;

    private boolean answer;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;


}
