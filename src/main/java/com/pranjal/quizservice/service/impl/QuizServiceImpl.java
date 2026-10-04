package com.pranjal.quizservice.service.impl;

import com.pranjal.quizservice.dto.request.QuizRequest;
import com.pranjal.quizservice.dto.response.QuizResponse;
import com.pranjal.quizservice.entity.Quiz;
import com.pranjal.quizservice.exception.ResourceNotFoundException;
import com.pranjal.quizservice.mapper.QuizMapper;
import com.pranjal.quizservice.repository.QuizRepository;
import com.pranjal.quizservice.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    @Override
    public QuizResponse create(QuizRequest request) {
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimitMinutes(request.getTimeLimitMinutes());
        quiz.setPublished(request.isPublished());
        return quizMapper.toResponse(quizRepository.save(quiz));
    }

    @Override
    public QuizResponse getById(UUID id) {
        return quizMapper.toResponse(findQuizOrThrow(id));
    }

    @Override
    public List<QuizResponse> getAll() {
        return quizRepository.findAll().stream()
                .map(quizMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public QuizResponse update(UUID id, QuizRequest request) {
        Quiz quiz = findQuizOrThrow(id);
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimitMinutes(request.getTimeLimitMinutes());
        quiz.setPublished(request.isPublished());
        return quizMapper.toResponse(quizRepository.save(quiz));
    }

    @Override
    public void delete(UUID id) {
        Quiz quiz = findQuizOrThrow(id);
        quizRepository.delete(quiz);
    }

    private Quiz findQuizOrThrow(UUID id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
    }
}