package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void userDelete(UserEntity user) {
        entityManager.remove(user);
    }

    public UserEntity createUser(UserEntity userEntity){
        entityManager.persist(userEntity);
        return userEntity;
    }


    public UserEntity getUserByContent(final String content){
        try {
            return entityManager.createNamedQuery("userByContent", UserEntity.class).setParameter("content", content)
                    .getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }

    public UserEntity getUserByUserName(final String userName){
        try {
            return entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", userName)
                    .getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }

    public UserEntity getUserByEmailAddress(final String emailAddress){
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("emailAddress",emailAddress)
                    .getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }
    public UserEntity getUser(String uuid){
        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", uuid).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public UserAuthTokenEntity getAuthToken(final String authToken) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class)
                    .setParameter("accessToken", authToken).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity){
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    public UserAuthTokenEntity getUserAuthTokenByAccessToken(final String accessToken){
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken",accessToken)
                    .getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }

    public void updateLogOutTime(final UserAuthTokenEntity userAuthTokenEntity){
        entityManager.merge(userAuthTokenEntity);
    }

    public UserAuthTokenEntity getUserAuthToken(String bearerToken) {
        UserAuthTokenEntity userAuthEntity;
        try {
            userAuthEntity = entityManager.createNamedQuery("userAuthTokenByAccessToken",
                    UserAuthTokenEntity.class).setParameter("accessToken", bearerToken.trim()).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return userAuthEntity;
    }
}
