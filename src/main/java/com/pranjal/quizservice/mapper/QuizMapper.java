package com.pranjal.quizservice.mapper;

import com.pranjal.quizservice.dto.response.QuestionResponse;
import com.pranjal.quizservice.dto.response.QuizResponse;
import com.pranjal.quizservice.entity.Quiz;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuizMapper {

    public QuizResponse toResponse(Quiz quiz) {
        List<QuestionResponse> questions = quiz.getQuestions().stream()
                .map(this::toQuestionResponse)
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

    private QuestionResponse toQuestionResponse(com.pranjal.quizservice.entity.Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getText(),
                question.getType(),
                question.getOrderIndex(),
                question.getPoints());
    }
}