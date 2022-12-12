package id.ilufa.quiz;

import id.ilufa.quiz.dao.ExamDao;
import id.ilufa.quiz.dao.QuestionDao;
import id.ilufa.quiz.entity.Exam;
import id.ilufa.quiz.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuizApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	ExamDao examDao;
	@Test
	void createExam() {
		Exam mathExam = new Exam();
		mathExam.setSubject("Bahasa Indonesia");
		examDao.save(mathExam);
		System.out.println(">>> exam id: " + mathExam.getId());
	}

	@Autowired
	QuestionDao questionDao;
	@Test
	void createQuestion() {
		String examId = "7b7fd257-2541-433a-bbd7-92a2ab64921c";
		Exam math1 = examDao.findById(examId)
				.orElseThrow(() -> new IllegalArgumentException(">>> exam not found, id: " + examId));
		Question question = new Question();
		question.setExam(math1);
		question.setText("1 + 1 = ");
		questionDao.save(question);
		System.out.println(">>> question id: " + question.getId());
	}
}
