package com.endeymus.parser.repository;

import com.endeymus.parser.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mark Shamray
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
