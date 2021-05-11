package com.endeymus.parser.service;

import com.endeymus.parser.entity.Monitoring;
import com.endeymus.parser.repository.MonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Service("monitoringService")
@Transactional
public class MonitoringServiceImpl implements MonitoringService {

    private MonitoringRepository monitoringRepository;

    @Autowired
    public void setMonitoringRepository(MonitoringRepository monitoringRepository) {
        this.monitoringRepository = monitoringRepository;
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Monitoring> findAll() {
        return monitoringRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monitoring> findByIdUser(Long id) {
        return em.createNamedQuery(Monitoring.SQL_FIND_BY_ID_USER, Monitoring.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public Monitoring save(Monitoring monitoring) {
        return monitoringRepository.save(monitoring);
    }

    @Override
    public void delete(Monitoring monitoring) {
        monitoringRepository.delete(monitoring);
    }

    @Override
    @Transactional(readOnly = true)
    public Monitoring findOne(Long id) {
        return monitoringRepository.getOne(id);
    }
}
