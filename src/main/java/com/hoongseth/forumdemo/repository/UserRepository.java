package com.hoongseth.forumdemo.repository;

import com.hoongseth.forumdemo.model.ForumUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<ForumUser, String> {
    ForumUser findByUsername(String username);
    ForumUser findByEmail(String email);
    ForumUser save(ForumUser forumUser);
}
