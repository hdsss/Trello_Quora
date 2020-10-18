package com.upgrad.quora.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "question" , schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "getAllQuestions" , query = "select q from QuestionEntity q"),
                //query to get question by ID
                @NamedQuery(name = "questionByUuid", query = "select q from QuestionEntity q INNER JOIN UserEntity u on q.user = u.id where q.uuid =:questionId"),
                //query to get all questions by user
                @NamedQuery(name = "getAllQuestionsByUser", query = "select q from QuestionEntity q INNER JOIN UserEntity u on q.user = u.id where u.uuid =:uuid"),
                //query to get question by ID
                @NamedQuery(name = "questionById", query = "select q from QuestionEntity q where q.uuid = :uuid")
        }
)
public class QuestionEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private String uuid;

    @Column(name = "CONTENT")
    @NotNull
    private String content;

    @Column(name = "DATE")
    @NotNull
    private ZonedDateTime Date;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getDate() {
        return Date;
    }

    public void setDate(ZonedDateTime date) {
        Date = date;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
