package com.endeymus.parser.repository;

import com.endeymus.parser.entity.Monitoring;
import com.endeymus.parser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Mark Shamray
 */
public interface MonitoringRepository extends JpaRepository<Monitoring, Long> {
    List<Monitoring> findByIdUserAndIdUserHashKey(User user, int hashKey);
}
