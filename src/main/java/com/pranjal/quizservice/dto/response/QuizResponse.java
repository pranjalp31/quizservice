package com.pranjal.quizservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    private UUID id;
    private String title;
    private String description;
    private Integer timeLimitMinutes;
    private boolean published;
    private List<QuestionResponse> questions;
    private Instant createdAt;
    private Instant updatedAt;
}