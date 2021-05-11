package com.endeymus.parser.service;

import com.endeymus.parser.entity.Channel;
import com.endeymus.parser.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Service("channelService")
@Transactional
public class ChannelServiceImpl implements ChannelService {

    private ChannelRepository channelRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public void setChannelRepository(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Channel> findByIdUser(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Channel findOne(Long id) {
        return channelRepository.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Channel findByIdInternal(Long id) {
        return em.createNamedQuery(Channel.FIND_BY_ID_INTERNAL, Channel.class)
                .setParameter("id_internal", id)
                .getSingleResult();
    }
}
