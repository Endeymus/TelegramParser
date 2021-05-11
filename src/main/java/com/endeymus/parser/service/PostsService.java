package com.endeymus.parser.service;

import com.endeymus.parser.entity.Channel;
import com.endeymus.parser.entity.Posts;

import java.util.List;

/**
 * @author Mark Shamray
 */
public interface PostsService {
    List<Posts> findAll();
    List<Posts> findByChannel(Channel channel);
    Posts findOne(Long id);
    Posts findByIdInternal(Long id);
    Posts save(Posts posts);
    void delete(Posts posts);
    List<Posts> findLastTopPosts(Long idChannel);
    Posts findLastPosts(Long idChannel);
}
