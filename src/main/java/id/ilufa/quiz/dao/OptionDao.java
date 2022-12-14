package id.ilufa.quiz.dao;

import id.ilufa.quiz.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionDao extends JpaRepository<Option, String> {
}
