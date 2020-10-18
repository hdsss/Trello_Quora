package com.upgrad.quora.service.business;

import com.upgrad.quora.service.common.ServiceConstant;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class AdminService {

    @Autowired
    UserDao userDao;

    public void userDelete(String uUid, String bearerToken) throws UserNotFoundException, AuthorizationFailedException {
        UserAuthEntity userAuthEntity = userDao.getUserAuthToken(bearerToken);
        UserEntity user = userDao.getUser(uUid);
        if (userAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        if(ZonedDateTime.now().isAfter(userAuthEntity.getExpiresAt())){
            throw new AuthorizationFailedException("ATHR-003", "User is signed out");
        }
        if(user.getRole().equals(ServiceConstant.NON_ADMIN)){
            throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin");
        }
        if (user == null) {
            throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");
        }
        userDao.userDelete(user);
    }
}
