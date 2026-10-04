package com.pranjal.quizservice.service.impl;

import com.pranjal.quizservice.dto.request.QuizRequest;
import com.pranjal.quizservice.dto.response.QuizResponse;
import com.pranjal.quizservice.entity.Quiz;
import com.pranjal.quizservice.exception.ResourceNotFoundException;
import com.pranjal.quizservice.mapper.QuizMapper;
import com.pranjal.quizservice.repository.QuizRepository;
import com.pranjal.quizservice.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    @Override
    @Transactional
    public QuizResponse create(QuizRequest request) {
        log.info("Creating quiz with title '{}'", request.getTitle());
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimitMinutes(request.getTimeLimitMinutes());
        quiz.setPublished(request.isPublished());
        Quiz saved = quizRepository.save(quiz);
        log.info("Created quiz with id {}", saved.getId());
        return quizMapper.toResponse(saved);
    }

    @Override
    public QuizResponse getById(UUID id) {
        log.debug("Fetching quiz with id {}", id);
        return quizMapper.toResponse(findQuizOrThrow(id));
    }

    @Override
    public List<QuizResponse> getAll() {
        log.debug("Fetching all quizzes");
        return quizRepository.findAll().stream()
                .map(quizMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuizResponse update(UUID id, QuizRequest request) {
        log.info("Updating quiz with id {}", id);
        Quiz quiz = findQuizOrThrow(id);
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimitMinutes(request.getTimeLimitMinutes());
        quiz.setPublished(request.isPublished());
        return quizMapper.toResponse(quizRepository.save(quiz));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("Deleting quiz with id {}", id);
        Quiz quiz = findQuizOrThrow(id);
        quizRepository.delete(quiz);
    }

    private Quiz findQuizOrThrow(UUID id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Quiz not found with id {}", id);
                    return new ResourceNotFoundException("Quiz not found with id: " + id);
                });
    }
}