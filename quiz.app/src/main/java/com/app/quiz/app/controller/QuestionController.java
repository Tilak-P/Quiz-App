package com.app.quiz.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.quiz.app.model.Question;
import com.app.quiz.app.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getallquestions() {
        return questionService.getallquestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @GetMapping("/")
    public ResponseEntity<List<Question>> getQuestions(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "limit", defaultValue = "25") int limit) {
        return questionService.getQuestions(page, limit);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("updateQuestion/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }
}
