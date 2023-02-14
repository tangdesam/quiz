package id.ilufa.quiz.controller;

import id.ilufa.quiz.dao.OptionDao;
import id.ilufa.quiz.dao.QuestionDao;
import id.ilufa.quiz.entity.Exam;
import id.ilufa.quiz.entity.Option;
import id.ilufa.quiz.entity.Question;
import id.ilufa.quiz.exception.QuizException;
import id.ilufa.quiz.service.OptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("/option")
public class OptionController {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private OptionService optionService;

    @GetMapping("/create")
    public String create(Option option, @RequestParam("questionId") Long questionId) {
        return("option/create");
    }

    @PostMapping("/save")
    public String save(@Valid Option option, BindingResult result, HttpServletRequest request, Model model) {
        Long questionId = -1l;
        Question question = null;
        String questionIdStr = request.getParameter("questionId");
        if (questionIdStr != null) {
            questionId = Long.parseLong(questionIdStr);
            question = questionDao.findById(questionId).orElse(null);
            if (question == null) {
                result.addError(new ObjectError("questionId", "Question not found, invalid questionId"));
            }
        }
        else {
            result.addError(new ObjectError("questionId", "Required parameter questionId"));
        }

        if (result.hasErrors()) {
            return "option/create";
        }

        option.setQuestion(question);
        try {
            optionService.save(option);
            return "redirect:/exam/edit/" + question.getExam().getId();
        } catch (SQLException e) {
            result.addError(new ObjectError("SQLException", e.getMessage()));
            return "option/create";
        } catch (QuizException e) {
            result.addError(new ObjectError("QuizException", e.getMessage()));
            return "option/create";
        }

    }

}
