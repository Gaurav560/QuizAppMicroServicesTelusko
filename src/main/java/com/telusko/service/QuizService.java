package com.telusko.service;

import com.telusko.model.Question;
import com.telusko.model.QuestionDto;
import com.telusko.model.Quiz;
import com.telusko.model.Response;
import com.telusko.repo.QuestionRepo;
import com.telusko.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private QuizRepo quizRepo;
    private final QuestionRepo questionRepo;

    public QuizService(QuizRepo quizRepo, QuestionRepo questionRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
    }

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<QuestionDto>> getQuizQuestions(Integer id) {
        Optional<Quiz> quizOptional = quizRepo.findById(id);
        if (!quizOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Quiz quiz = quizOptional.get();
        List<Question> questionsFromDB = quiz.getQuestions();

        List<QuestionDto> questionsForUser = new ArrayList<>();
        for (Question q : questionsFromDB) {
            questionsForUser.add(new QuestionDto(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()));
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responseList) {
        Optional<Quiz> quizOptional = quizRepo.findById(id);
        if (!quizOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Quiz quiz = quizOptional.get();
        List<Question> questions = quiz.getQuestions();

        int rightAns = 0;
        for (int i = 0; i < responseList.size(); i++) {
            Response r = responseList.get(i);
            // Assuming the responseList is in the same order as the questions list
            if (i < questions.size() && r.getResponse().equals(questions.get(i).getRightAnswer())) {
                rightAns++;
            }
        }
        return new ResponseEntity<>(rightAns, HttpStatus.OK);
    }

}
