package com.Azhaan.onlineTest.service;

import com.Azhaan.onlineTest.model.Question;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    Question createQuestion(Question question);
    List<Question> getAllQuestions();
    Optional<Question> getQuestionById(Long id); //Optional<> means it will handle the case when Q do not exist
    void deleteQuestion(Long id);
    Question updateQuestion(Long id ,Question question) throws ChangeSetPersister.NotFoundException;
    List<String> getAllSubjects();
    List<Question> getQuestionsForUser(Integer noOfQuestions ,String subject);
}
