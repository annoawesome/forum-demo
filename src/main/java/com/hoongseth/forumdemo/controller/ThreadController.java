package com.hoongseth.forumdemo.controller;

import com.hoongseth.forumdemo.model.ForumThread;
import com.hoongseth.forumdemo.model.ThreadPreview;
import com.hoongseth.forumdemo.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ThreadPreview> getThreads(@RequestParam int page, @RequestParam int size) {
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

    @PostMapping("/threads")
    public ThreadPreview createThread(@RequestBody ForumThread forumThread) {
        return ThreadPreview.fromForumThread(threadService.postThread(forumThread));
    }
}
