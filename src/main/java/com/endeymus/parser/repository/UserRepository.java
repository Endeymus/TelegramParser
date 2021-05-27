package com.endeymus.parser.repository;

import com.endeymus.parser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mark Shamray
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findByHashKey(int hashKey);
}
