package com.endeymus.parser.repository;

import com.endeymus.parser.entity.Channel;
import com.endeymus.parser.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Mark Shamray
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByIdChannel(Channel channel);
}
