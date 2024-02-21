package com.telusko.repo;

import com.googlecode.cqengine.query.simple.In;
import com.telusko.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {

}
