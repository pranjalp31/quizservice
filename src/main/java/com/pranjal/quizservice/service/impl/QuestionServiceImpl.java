package com.pranjal.quizservice.service.impl;

import com.pranjal.quizservice.dto.request.QuestionRequest;
import com.pranjal.quizservice.dto.response.QuestionResponse;
import com.pranjal.quizservice.entity.Question;
import com.pranjal.quizservice.entity.Quiz;
import com.pranjal.quizservice.exception.ResourceNotFoundException;
import com.pranjal.quizservice.repository.QuestionRepository;
import com.pranjal.quizservice.repository.QuizRepository;
import com.pranjal.quizservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    @Override
    public QuestionResponse create(UUID quizId, QuestionRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(request.getText());
        question.setType(request.getType());
        question.setOrderIndex(request.getOrderIndex());
        question.setPoints(request.getPoints());

        return toResponse(questionRepository.save(question));
    }

    @Override
    public QuestionResponse getById(UUID id) {
        return toResponse(findQuestionOrThrow(id));
    }

    @Override
    public List<QuestionResponse> getAllByQuizId(UUID quizId) {
        return questionRepository.findAll().stream()
                .filter(q -> q.getQuiz().getId().equals(quizId))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionResponse update(UUID id, QuestionRequest request) {
        Question question = findQuestionOrThrow(id);
        question.setText(request.getText());
        question.setType(request.getType());
        question.setOrderIndex(request.getOrderIndex());
        question.setPoints(request.getPoints());
        return toResponse(questionRepository.save(question));
    }

    @Override
    public void delete(UUID id) {
        Question question = findQuestionOrThrow(id);
        questionRepository.delete(question);
    }

    private Question findQuestionOrThrow(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
    }

    private QuestionResponse toResponse(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getText(),
                question.getType(),
                question.getOrderIndex(),
                question.getPoints());
    }
}