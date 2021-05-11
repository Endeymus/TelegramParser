package com.endeymus.parser.repository;

import com.endeymus.parser.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mark Shamray
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
