package com.telusko.service;

import com.telusko.model.Question;
import com.telusko.model.Quiz;
import com.telusko.repo.QuestionRepo;
import com.telusko.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuestionRepo questionRepo;



    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {

        List<Question> questions=questionRepo.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
