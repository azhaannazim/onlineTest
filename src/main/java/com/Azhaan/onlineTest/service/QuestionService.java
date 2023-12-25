package com.Azhaan.onlineTest.service;

import com.Azhaan.onlineTest.model.Question;
import com.Azhaan.onlineTest.repository.QuestionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{
    private final QuestionRepo questionRepo;
    @Override
    public Question createQuestion(Question question) {
        return questionRepo.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepo.findById(id);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepo.deleteById(id);
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = this.getQuestionById(id);
        if(theQuestion.isPresent()){
            Question updatedQuestion = theQuestion.get();
            updatedQuestion.setQuestion(question.getQuestion());
            updatedQuestion.setChoices(question.getChoices());
            updatedQuestion.setCorrectAnswer(question.getCorrectAnswer());

            return questionRepo.save(updatedQuestion);
        }
        else{
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepo.findDistinctSubject();
    }

    @Override
    public List<Question> getQuestionsForUser(Integer noOfQuestions, String subject) {
        Pageable pageable = (Pageable) PageRequest.of(0 ,noOfQuestions);
        return questionRepo.findBySubject(subject ,pageable).getContent();
    }
}
