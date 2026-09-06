package com.pranjal.quizservice.service;

import com.pranjal.quizservice.dto.request.QuizRequest;
import com.pranjal.quizservice.dto.response.QuestionResponse;
import com.pranjal.quizservice.dto.response.QuizResponse;
import com.pranjal.quizservice.entity.Quiz;
import com.pranjal.quizservice.exception.ResourceNotFoundException;
import com.pranjal.quizservice.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizResponse create(QuizRequest request) {
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimitMinutes(request.getTimeLimitMinutes());
        quiz.setPublished(request.isPublished());
        return toResponse(quizRepository.save(quiz));
    }

    public QuizResponse getById(UUID id) {
        return toResponse(findQuizOrThrow(id));
    }

    public List<QuizResponse> getAll() {
        return quizRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public QuizResponse update(UUID id, QuizRequest request) {
        Quiz quiz = findQuizOrThrow(id);
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimitMinutes(request.getTimeLimitMinutes());
        quiz.setPublished(request.isPublished());
        return toResponse(quizRepository.save(quiz));
    }

    public void delete(UUID id) {
        Quiz quiz = findQuizOrThrow(id);
        quizRepository.delete(quiz);
    }

    private Quiz findQuizOrThrow(UUID id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
    }

    private QuizResponse toResponse(Quiz quiz) {
        List<QuestionResponse> questions = quiz.getQuestions().stream()
                .map(q -> new QuestionResponse(
                        q.getId(), q.getText(), q.getType(), q.getOrderIndex(), q.getPoints()))
                .collect(Collectors.toList());

        return new QuizResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getTimeLimitMinutes(),
                quiz.isPublished(),
                questions,
                quiz.getCreatedAt(),
                quiz.getUpdatedAt());
    }
}