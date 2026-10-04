package com.pranjal.quizservice.service;

import com.pranjal.quizservice.dto.request.QuizRequest;
import com.pranjal.quizservice.dto.response.QuizResponse;

import java.util.List;
import java.util.UUID;

public interface QuizService {

    QuizResponse create(QuizRequest request);

    QuizResponse getById(UUID id);

    List<QuizResponse> getAll();

    QuizResponse update(UUID id, QuizRequest request);

    void delete(UUID id);
}