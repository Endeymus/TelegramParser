package com.endeymus.parser.service;

import com.endeymus.parser.entity.Channel;

import java.util.List;

/**
 * @author Mark Shamray
 */
public interface ChannelService {
    List<Channel> findAll();
    Channel save(Channel channel);
    List<Channel> findByIdUser(Long id);
    Channel findOne(Long id);
    Channel findByIdInternal(Long id);
}
