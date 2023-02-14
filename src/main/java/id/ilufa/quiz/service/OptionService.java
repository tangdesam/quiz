package id.ilufa.quiz.service;

import id.ilufa.quiz.dao.OptionDao;
import id.ilufa.quiz.entity.Option;
import id.ilufa.quiz.entity.Question;
import id.ilufa.quiz.exception.QuizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class OptionService {
    @Autowired
    private OptionDao optionDao;

    @Transactional(rollbackFor = {SQLException.class, QuizException.class})
    public void save(Option option) throws SQLException, QuizException {
        Question question = option.getQuestion();
        if (question == null) {
            throw new QuizException("Question not found (exception from OptionService)");
        }
        if (option.isAnswer()) {
            // make other option as false answer
            for (Option otherOption : question.getOptionList()) {
                otherOption.setAnswer(false);
                optionDao.save(otherOption);
            }
        }
        optionDao.save(option);
    }
}
