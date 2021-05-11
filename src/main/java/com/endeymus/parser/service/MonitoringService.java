package com.endeymus.parser.service;

import com.endeymus.parser.entity.Monitoring;

import java.util.List;

/**
 * @author Mark Shamray
 */
public interface MonitoringService {
    List<Monitoring> findAll();
    List<Monitoring> findByIdUser(Long id);
    Monitoring save(Monitoring monitoring);
    void delete(Monitoring monitoring);
    Monitoring findOne(Long id);
}
