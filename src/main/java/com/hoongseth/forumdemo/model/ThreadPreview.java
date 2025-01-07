package com.hoongseth.forumdemo.model;

import lombok.Data;

import java.util.Date;

@Data
public class ThreadPreview {
    private String id;
    private String title;
    private String contentPreview;
    private String author;
    private Date createdAt;

    public static ThreadPreview fromForumThread(ForumThread forumThread) {
        ThreadPreview threadPreview = new ThreadPreview();
        threadPreview.setId(forumThread.getId());
        threadPreview.setTitle(forumThread.getTitle());
        threadPreview.setAuthor(forumThread.getAuthor());
        threadPreview.setContentPreview(forumThread.getContent().substring(0, Math.min(100, forumThread.getContent().length())));
        threadPreview.setCreatedAt(forumThread.getCreatedAt());
        return threadPreview;
    }
}
