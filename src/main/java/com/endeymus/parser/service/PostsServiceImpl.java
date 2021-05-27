package com.endeymus.parser.service;

import com.endeymus.parser.entity.Channel;
import com.endeymus.parser.entity.Posts;
import com.endeymus.parser.repository.PostsRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Service("postsService")
@Transactional
public class PostsServiceImpl implements PostsService {
    private static final int MAX_RESULT = 100;
    private static final int SINGLE_RESULT = 1;
    @Autowired
    private PostsRepository postsRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Posts> findAll() {
        return postsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Posts> findByChannel(Channel channel) {
        return postsRepository.findByIdChannel(channel);
    }

    @Override
    @Transactional(readOnly = true)
    public Posts findOne(Long id) {
        return postsRepository.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Posts findByIdInternal(Long id) {
        Posts result;
        try {
            result = em.createNamedQuery(Posts.FIND_BY_ID_INTERNAL, Posts.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            result = null;
        }
        return result;
    }

    @Override
    public Posts save(Posts posts) {
        return postsRepository.save(posts);
    }

    @Override
    public void delete(Posts posts) {
        postsRepository.delete(posts);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Posts> findLastTopPosts(Long idChannel) {
        return em.createNamedQuery(Posts.FIND_LAST_TOP_POSTS_BY_CHANNEL_ID, Posts.class)
                .setParameter("id_channel", idChannel)
                .setMaxResults(MAX_RESULT)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Posts findLastPosts(Long idChannel) {
        Posts channel;
        try {
            channel = em.createNamedQuery(Posts.FIND_LAST_TOP_POSTS_BY_CHANNEL_ID, Posts.class)
                    .setParameter("id_channel", idChannel)
                    .setMaxResults(SINGLE_RESULT)
                    .getSingleResult();
        } catch (NoResultException nre) {
            channel = null;
        }
        return channel;
    }
}
