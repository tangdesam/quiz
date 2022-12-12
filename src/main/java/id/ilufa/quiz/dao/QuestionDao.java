package id.ilufa.quiz.dao;

import id.ilufa.quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDao extends JpaRepository<Question, Long> {
}
