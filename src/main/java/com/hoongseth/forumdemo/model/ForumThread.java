package com.hoongseth.forumdemo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("threads")
public class ForumThread {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;

    private int likes;
    private int comments;
    private int views;
    private List<ForumThread> children;
    private String parentId;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
