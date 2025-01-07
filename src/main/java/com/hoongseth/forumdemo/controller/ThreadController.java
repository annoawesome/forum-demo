package com.hoongseth.forumdemo.controller;

import com.hoongseth.forumdemo.model.ForumThread;
import com.hoongseth.forumdemo.model.ThreadPreview;
import com.hoongseth.forumdemo.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ThreadController {
    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/threads")
    @Secured("ROLE_USER")
    public List<ThreadPreview> getThreads(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return threadService.getForumThreads(page, size).getContent()
                .stream()
                .map(ThreadPreview::fromForumThread)
                .toList();
    }

    @GetMapping("/threads/preview/{id}")
    public ThreadPreview getThreadById(@PathVariable String id) {
        return threadService.getThreadPreview(id);
    }

    @GetMapping("/threads/{id}")
    public ForumThread getThread(@PathVariable String id) {
        return threadService.getForumThread(id);
    }

    @PatchMapping("/threads/{id}/view")
    public ResponseEntity<ForumThread> viewThread(@PathVariable String id) {
        return ResponseEntity.ok(threadService.incrementViews(threadService.getForumThread(id)));
    }

    @PatchMapping("/threads/{id}/like")
    @Secured("ROLE_USER")
    public ResponseEntity<ForumThread> likeThread(@PathVariable String id) {
        ForumThread thread = threadService.getForumThread(id);

        if (thread == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(threadService.incrementLike(thread));
    }

    @PostMapping("/threads")
    @Secured("ROLE_USER")
    public ResponseEntity<String> createThread(@RequestBody ForumThread forumThread) {
        if (forumThread.getAuthor() == null) {
            ResponseEntity.badRequest().body("Author is required");
        }

        if (forumThread.getTitle() == null || forumThread.getTitle().isEmpty()) {
            ResponseEntity.badRequest().body("Title cannot be empty");
        }

        if (forumThread.getContent() == null || forumThread.getContent().isEmpty()) {
            ResponseEntity.badRequest().body("Content cannot be empty");
        }

        forumThread.setLikes(0);
        forumThread.setComments(0);
        forumThread.setViews(0);
        forumThread.setChildren(List.of());

        return ResponseEntity.ok(threadService.postThread(forumThread).getId());
    }

    @PostMapping("/threads/{id}/reply")
    @Secured("ROLE_USER")
    public ResponseEntity<String> replyThread(@PathVariable String id, @RequestBody ForumThread reply) {
        ForumThread thread = threadService.getForumThread(id);

        if (thread == null) {
            return ResponseEntity.notFound().build();
        }

        threadService.replyToThread(thread, reply);

        return ResponseEntity.ok(thread.getId());
    }

    @DeleteMapping("/threads/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<String> deleteThread(@PathVariable String id) {
        ForumThread thread = threadService.getForumThread(id);
        if (thread == null) {
            return ResponseEntity.notFound().build();
        }

        threadService.deleteThread(id);
        return ResponseEntity.ok().build();
    }
}
