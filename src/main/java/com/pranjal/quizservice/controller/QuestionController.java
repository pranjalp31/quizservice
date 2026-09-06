package com.pranjal.quizservice.controller;

import com.pranjal.quizservice.dto.request.QuestionRequest;
import com.pranjal.quizservice.dto.response.QuestionResponse;
import com.pranjal.quizservice.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/api/quizzes/{quizId}/questions")
    public ResponseEntity<QuestionResponse> create(
            @PathVariable UUID quizId, @Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.create(quizId, request));
    }

    @GetMapping("/api/quizzes/{quizId}/questions")
    public ResponseEntity<List<QuestionResponse>> getAllByQuiz(@PathVariable UUID quizId) {
        return ResponseEntity.ok(questionService.getAllByQuizId(quizId));
    }

    @GetMapping("/api/questions/{id}")
    public ResponseEntity<QuestionResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(questionService.getById(id));
    }

    @PutMapping("/api/questions/{id}")
    public ResponseEntity<QuestionResponse> update(
            @PathVariable UUID id, @Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.update(id, request));
    }

    @DeleteMapping("/api/questions/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}