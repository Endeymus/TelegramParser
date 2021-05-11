package com.endeymus.parser.repository;

import com.endeymus.parser.entity.Monitoring;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mark Shamray
 */
public interface MonitoringRepository extends JpaRepository<Monitoring, Long> {
}
