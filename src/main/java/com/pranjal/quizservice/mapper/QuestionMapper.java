package com.pranjal.quizservice.mapper;

import com.pranjal.quizservice.dto.response.QuestionResponse;
import com.pranjal.quizservice.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public QuestionResponse toResponse(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getText(),
                question.getType(),
                question.getOrderIndex(),
                question.getPoints());
    }
}