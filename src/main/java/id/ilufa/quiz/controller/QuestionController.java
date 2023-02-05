package id.ilufa.quiz.controller;

import id.ilufa.quiz.dao.ExamDao;
import id.ilufa.quiz.dao.QuestionDao;
import id.ilufa.quiz.entity.Exam;
import id.ilufa.quiz.entity.Question;
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

import java.util.Optional;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private QuestionDao questionDao;

    @GetMapping("/create")
    public String create(Question question, @RequestParam("examId") String examId) {
        return("question/create");
    }

    @PostMapping("/save")
    public String save(@Valid Question question, BindingResult result, HttpServletRequest request, Model model) {
        Exam exam = null;
        String examId = request.getParameter("examId");
        if (examId != null) {
            Optional<Exam> examOptional = examDao.findById(examId);
            if (examOptional.isPresent()) {
                exam = examOptional.get();
            }
            else {
                result.addError(new ObjectError("examId", "Exam not found, invalid examId"));
            }
        }
        else {
            result.addError(new ObjectError("examId", "Required parameter examId"));
        }

        if (result.hasErrors()) {
            return "question/create";
        }

        question.setExam(exam);
        questionDao.save(question);
        return "redirect:/exam/edit/" + question.getExam().getId();
    }

}
