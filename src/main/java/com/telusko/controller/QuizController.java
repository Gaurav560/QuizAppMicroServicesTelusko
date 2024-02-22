package com.telusko.controller;

import com.telusko.model.Question;
import com.telusko.model.QuestionDto;
import com.telusko.model.Response;
import com.telusko.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController
{

    private QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }


@PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam Integer numQ,@RequestParam String title){
     return quizService.createQuiz(category,numQ,title);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionDto>> getQuizQuestions(@PathVariable Integer id){
 return quizService.getQuizQuestions(id);
    }


    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responseList){
return quizService.calculateResult(id,responseList);

    }
}
