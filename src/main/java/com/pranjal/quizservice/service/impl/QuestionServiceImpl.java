package com.pranjal.quizservice.service.impl;

import com.pranjal.quizservice.dto.request.QuestionRequest;
import com.pranjal.quizservice.dto.response.QuestionResponse;
import com.pranjal.quizservice.entity.Question;
import com.pranjal.quizservice.entity.Quiz;
import com.pranjal.quizservice.exception.ResourceNotFoundException;
import com.pranjal.quizservice.mapper.QuestionMapper;
import com.pranjal.quizservice.repository.QuestionRepository;
import com.pranjal.quizservice.repository.QuizRepository;
import com.pranjal.quizservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional
    public QuestionResponse create(UUID quizId, QuestionRequest request) {
        log.info("Adding question to quiz {}", quizId);
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> {
                    log.warn("Quiz not found with id {}", quizId);
                    return new ResourceNotFoundException("Quiz not found with id: " + quizId);
                });

        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(request.getText());
        question.setType(request.getType());
        question.setOrderIndex(request.getOrderIndex());
        question.setPoints(request.getPoints());

        Question saved = questionRepository.save(question);
        log.info("Created question with id {} for quiz {}", saved.getId(), quizId);
        return questionMapper.toResponse(saved);
    }

    @Override
    public QuestionResponse getById(UUID id) {
        log.debug("Fetching question with id {}", id);
        return questionMapper.toResponse(findQuestionOrThrow(id));
    }

    @Override
    public Page<QuestionResponse> getAllByQuizId(UUID quizId, Pageable pageable) {
        log.debug("Fetching questions for quiz {}, page {}", quizId, pageable.getPageNumber());
        return questionRepository.findByQuizId(quizId, pageable)
                .map(questionMapper::toResponse);
    }

    @Override
    @Transactional
    public QuestionResponse update(UUID id, QuestionRequest request) {
        log.info("Updating question with id {}", id);
        Question question = findQuestionOrThrow(id);
        question.setText(request.getText());
        question.setType(request.getType());
        question.setOrderIndex(request.getOrderIndex());
        question.setPoints(request.getPoints());
        return questionMapper.toResponse(questionRepository.save(question));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("Deleting question with id {}", id);
        Question question = findQuestionOrThrow(id);
        questionRepository.delete(question);
    }

    private Question findQuestionOrThrow(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Question not found with id {}", id);
                    return new ResourceNotFoundException("Question not found with id: " + id);
                });
    }
}