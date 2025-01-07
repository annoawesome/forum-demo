package com.hoongseth.forumdemo.repository;

import com.hoongseth.forumdemo.model.ForumThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThreadRepository extends MongoRepository<ForumThread, String> {
    ForumThread findByTitle(String title);
    Optional<ForumThread> findById(String id);
    ForumThread save(ForumThread forumThread);
    Page<ForumThread> findAll(Pageable pageable);
}
