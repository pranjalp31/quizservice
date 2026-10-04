package com.pranjal.quizservice.service;

import com.pranjal.quizservice.dto.request.QuestionRequest;
import com.pranjal.quizservice.dto.response.QuestionResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    QuestionResponse create(UUID quizId, QuestionRequest request);

    QuestionResponse getById(UUID id);

    List<QuestionResponse> getAllByQuizId(UUID quizId);

    QuestionResponse update(UUID id, QuestionRequest request);

    void delete(UUID id);
}