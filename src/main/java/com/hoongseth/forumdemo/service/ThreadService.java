package com.hoongseth.forumdemo.service;

import com.hoongseth.forumdemo.model.ForumThread;
import com.hoongseth.forumdemo.model.ThreadPreview;
import com.hoongseth.forumdemo.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThreadService {
    ThreadRepository threadRepository;

    @Autowired
    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public ForumThread getForumThread(String id) {
        Optional<ForumThread> forumThread = threadRepository.findById(id);

        return forumThread.orElse(null);
    }

    public Page<ForumThread> getForumThreads(int page, int size) {
        Pageable pageable = PageRequest.of(page, Math.min(20, size), Sort.by(Sort.Order.desc("createdAt")));
        return threadRepository.findAll(pageable);
    }

    public ThreadPreview getThreadPreview(String id) {
        Optional<ForumThread> thread = threadRepository.findById(id);

        return thread.map(ThreadPreview::fromForumThread).orElse(null);
    }

    public ForumThread postThread(ForumThread thread) {
        return threadRepository.save(thread);
    }
}
