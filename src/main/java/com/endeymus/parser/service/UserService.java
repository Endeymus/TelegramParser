package com.endeymus.parser.service;

import com.endeymus.parser.entity.User;

import java.util.List;

/**
 * @author Mark Shamray
 */
public interface UserService {
    List<User> findAll();
    User findOne(Long id);
    User save(User user);
    void delete(User user);
    User findWithDetails(Long id);
    List<User> findAllWithDetails();
}
