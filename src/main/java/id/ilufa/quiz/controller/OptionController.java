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
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("/option")
public class OptionController {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private OptionService optionService;
    @Autowired
    private OptionDao optionDao;

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

    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        Option option = optionDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID:" + id));

        model.addAttribute("option", option);
        return "option/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid Option option, BindingResult result) {

        Option optionRow = optionDao.findById(option.getId()).orElse(null);
        if (optionRow == null) {
            result.addError(new ObjectError("optionId", "Option not found, invalid optionId"));
        }

        if (result.hasErrors()) {
            return "option/edit";
        }

        optionRow.setText(option.getText());
        optionRow.setAnswer(option.isAnswer());
        optionDao.save(optionRow);

        return "redirect:/exam/edit/" + optionRow.getQuestion().getExam().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, Model model) {
        Option option = optionDao.findById(id).orElse(null);
        if (option == null) {
            return "redirect:/exam/";
        }
        String examId = option.getQuestion().getExam().getId();
        optionDao.delete(option);
        return "redirect:/exam/edit/" + examId;
    }

}
