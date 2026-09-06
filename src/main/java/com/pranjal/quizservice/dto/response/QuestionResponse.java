package com.pranjal.quizservice.dto.response;

import com.pranjal.quizservice.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {

    private UUID id;
    private String text;
    private QuestionType type;
    private Integer orderIndex;
    private Integer points;
}