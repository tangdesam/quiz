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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Question question = questionDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID:" + id));

        model.addAttribute("question", question);
        return "question/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid Question question, BindingResult result) {
        if (result.hasErrors()) {
            return "question/edit";
        }
        // question.getExam() IS NULL, so we need to load from db
        Question questionRow = questionDao.findById(question.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid ID:" + question.getId()));
        questionRow.setText(question.getText());
        questionDao.save(questionRow);
        return "redirect:/exam/edit/" + questionRow.getExam().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        Question question = questionDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID:" + id));
        String examId = question.getExam().getId();
        questionDao.delete(question);
        return "redirect:/exam/edit/" + examId;
    }

}
