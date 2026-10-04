package com.pranjal.quizservice.service;

import com.pranjal.quizservice.dto.request.QuestionRequest;
import com.pranjal.quizservice.dto.response.QuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface QuestionService {

    QuestionResponse create(UUID quizId, QuestionRequest request);

    QuestionResponse getById(UUID id);

    Page<QuestionResponse> getAllByQuizId(UUID quizId, Pageable pageable);

    QuestionResponse update(UUID id, QuestionRequest request);

    void delete(UUID id);
}