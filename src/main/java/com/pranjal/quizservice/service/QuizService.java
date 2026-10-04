package com.pranjal.quizservice.service;

import com.pranjal.quizservice.dto.request.QuizRequest;
import com.pranjal.quizservice.dto.response.QuizResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface QuizService {

    QuizResponse create(QuizRequest request);

    QuizResponse getById(UUID id);

    Page<QuizResponse> getAll(Pageable pageable);

    QuizResponse update(UUID id, QuizRequest request);

    void delete(UUID id);
}