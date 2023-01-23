package id.ilufa.quiz.controller;

import id.ilufa.quiz.dao.ExamDao;
import id.ilufa.quiz.entity.Exam;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private ExamDao examDao;

    @GetMapping("/")
    public String index(Model model, Pageable pageable) {
        Page<Exam> exams = examDao.findAll(pageable);
        model.addAttribute("exams", exams);
        return("exam/index");
    }

    @GetMapping("/create")
    public String create(Exam exam) {
        return("exam/create");
    }

    @PostMapping("/save")
    public String save(@Valid Exam exam, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "exam/create";
        }
        examDao.save(exam);
        return "redirect:/exam/";
    }

}
