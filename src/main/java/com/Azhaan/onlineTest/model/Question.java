package com.Azhaan.onlineTest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String question;
    @NotBlank
    private String subject;
    @NotBlank
    private String questionType;
    @NotBlank
    @ElementCollection // to map it in a separate table , it is used to map strings
    private List<String> choices;
    @NotBlank
    @ElementCollection
    private List<String> correctAnswer;
}
