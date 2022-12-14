package id.ilufa.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Entity
public class Exam {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String subject;

    @OneToMany(mappedBy = "exam", fetch = FetchType.EAGER)
    private List<Question> questionList;
}
