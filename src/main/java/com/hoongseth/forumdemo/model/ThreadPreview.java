package com.hoongseth.forumdemo.model;

import lombok.Data;

import java.util.Date;

@Data
public class ThreadPreview {
    private String id;
    private String title;
    private String contentPreview;
    private String author;

    private int likes;
    private int comments;
    private int views;

    private Date createdAt;
    private Date updatedAt;

    public static ThreadPreview fromForumThread(ForumThread forumThread) {
        ThreadPreview threadPreview = new ThreadPreview();
        threadPreview.setId(forumThread.getId());
        threadPreview.setTitle(forumThread.getTitle());
        threadPreview.setAuthor(forumThread.getAuthor());
        threadPreview.setContentPreview(forumThread.getContent().substring(0, Math.min(100, forumThread.getContent().length())));

        threadPreview.setLikes(forumThread.getLikes());
        threadPreview.setComments(forumThread.getComments());
        threadPreview.setViews(forumThread.getViews());

        threadPreview.setCreatedAt(forumThread.getCreatedAt());
        threadPreview.setUpdatedAt(forumThread.getUpdatedAt());
        return threadPreview;
    }
}
