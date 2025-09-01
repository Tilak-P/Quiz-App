package com.app.quiz.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.quiz.app.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz,Integer>{

}
