package com.endeymus.parser.service;

import com.endeymus.parser.entity.User;
import com.endeymus.parser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;



    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findWithDetails(Long id) {
        return em.createNamedQuery(User.SQL_FIND_WITH_DETAILS, User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllWithDetails() {
        return em.createNamedQuery(User.SQL_FIND_ALL_WITH_DETAILS, User.class)
                .getResultList();
    }

    @Override
    public User findByHashKey(int hashKey) {
        return userRepository.findByHashKey(hashKey);
    }
}
