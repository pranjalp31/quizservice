package com.pranjal.quizservice.controller;

import com.pranjal.quizservice.dto.request.QuizRequest;
import com.pranjal.quizservice.dto.response.QuizResponse;
import com.pranjal.quizservice.service.QuizService;
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
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizResponse> create(@Valid @RequestBody QuizRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quizService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAll() {
        return ResponseEntity.ok(quizService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(quizService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizResponse> update(@PathVariable UUID id, @Valid @RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }
}