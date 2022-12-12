package id.ilufa.quiz.dao;

import id.ilufa.quiz.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamDao extends JpaRepository<Exam, String> {
}
