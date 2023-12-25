package com.Azhaan.onlineTest.controller;

import com.Azhaan.onlineTest.model.Question;
import com.Azhaan.onlineTest.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuestionController {
    private final IQuestionService questionService;
    @PostMapping("/create-new-question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question){
        Question createdQuestion = questionService.createQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion); //status code - 201(CREATED)
    }                                                                               //used after successfully creating
    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions); //status code - 200(OK)
    }
    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = questionService.getQuestionById(id);
        if(theQuestion.isPresent()){
            return ResponseEntity.ok(theQuestion.get());
        }
        else{
            throw new ChangeSetPersister.NotFoundException();
        }
    }
    @PutMapping("/question/{id}/update")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id ,@RequestBody Question question)
            throws ChangeSetPersister.NotFoundException {
        Question updatedQuestion = questionService.updateQuestion(id ,question);

        return ResponseEntity.ok(updatedQuestion);
    }
    @DeleteMapping("/question/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects(){
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }
    @GetMapping("/quiz/fetch-questions-for-user")
    public ResponseEntity<List<Question>> getQuestionsForUser
            (@RequestParam Integer noOfQuestions ,@RequestParam String subject){
        List<Question> allQuestions = questionService.getQuestionsForUser(noOfQuestions ,subject);
        List<Question> mutableQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuestions);

        int availableQuestions = Math.min(noOfQuestions ,mutableQuestions.size()); //if user asked for 5 Questions ,but we have only 2 Qs for that subject
        List<Question> randomQuestions = mutableQuestions.subList(0 ,availableQuestions);
        return ResponseEntity.ok(randomQuestions);
    }




}
