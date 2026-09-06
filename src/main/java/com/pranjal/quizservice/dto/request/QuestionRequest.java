package com.pranjal.quizservice.dto.request;

import com.pranjal.quizservice.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {

    @NotBlank(message = "Question text is required")
    private String text;

    @NotNull(message = "Question type is required")
    private QuestionType type;

    private Integer orderIndex;
    private Integer points;
}