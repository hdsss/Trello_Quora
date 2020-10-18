package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionBusinessService {

    @Autowired
    private QuestionDao questionDao;

    public QuestionEntity validateQuestion(String questionId) throws InvalidQuestionException {
        QuestionEntity questionEntity = questionDao.getQuestionByUuid(questionId);
        if(questionEntity==null) {
            throw new InvalidQuestionException("QUES-001","The question entered is invalid");
        }
        return questionEntity;
    }

    public QuestionEntity validateQuestion(String questionId, boolean isForGetAllAnswers) throws InvalidQuestionException {
        QuestionEntity questionEntity = questionDao.getQuestionByUuid(questionId);
        if(questionEntity==null && isForGetAllAnswers) {
            throw new InvalidQuestionException("QUES-001","The question with entered uuid whose details are to be seen does not exist");
        }
        return questionEntity;
    }
}