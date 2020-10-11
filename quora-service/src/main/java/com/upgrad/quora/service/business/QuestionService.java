package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity createQuestion(QuestionEntity question, String authToken) throws AuthorizationFailedException {
       UserAuthTokenEntity authTokenEntity= userDao.getAuthToken(authToken);
       //Checks if authToken is valid or not.
       if(authTokenEntity==null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in.");
       }
        question.setUserId(authTokenEntity.getUser());

       if(authTokenEntity.getLogoutAt()!=null) {
           LocalDateTime logoutTime = authTokenEntity.getLogoutAt().toLocalDateTime();
           LocalDateTime currentTime = LocalDateTime.now();
           //Checks  logged out time to determine if user is currently signed in or not.
           if (logoutTime.isBefore(currentTime)) {
               throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");
           }
       }
       return questionDao.createQuestion(question);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<QuestionEntity> getAllQuestion(String authToken) throws AuthorizationFailedException {
        UserAuthTokenEntity authTokenEntity= userDao.getAuthToken(authToken);
        //Checks if authToken is valid or not.
        if(authTokenEntity==null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in.");
        }
        if(authTokenEntity.getLogoutAt()!=null) {
            LocalDateTime logoutTime = authTokenEntity.getLogoutAt().toLocalDateTime();
            LocalDateTime currentTime = LocalDateTime.now();
            //Checks  logged out time to determine if user is currently signed in or not.
            if (logoutTime.isBefore(currentTime)) {
                throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");
            }
        }
        UserEntity user=authTokenEntity.getUser();
        return questionDao.getAllQuestions(user);
    }

}