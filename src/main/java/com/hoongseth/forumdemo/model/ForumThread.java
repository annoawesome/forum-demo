package com.hoongseth.forumdemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("threads")
public class ForumThread {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private long createdAt;
}
