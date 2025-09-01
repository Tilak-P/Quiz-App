package com.app.quiz.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.quiz.app.model.Question;
import com.app.quiz.app.dao.QuestionDao;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getallquestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question updatedQuestion) {
        try {
            Question existingQuestion = questionDao.findById(id).orElse(null);
            if (existingQuestion == null) {
                return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
            }
            BeanUtils.copyProperties(updatedQuestion, existingQuestion, "id");
            questionDao.save(existingQuestion);
            return new ResponseEntity<>("UPDATED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            if (!questionDao.existsById(id)) {
                return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
            }
            questionDao.deleteById(id);
            return new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestions(int page, int limit) {
        try {
            if (page > 0)
                page -= 1;

            Pageable pageableRequest = PageRequest.of(page, limit);
            Page<Question> questionsPage = questionDao.findAll(pageableRequest);
            List<Question> questions = new ArrayList<>(questionsPage.getContent());
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
