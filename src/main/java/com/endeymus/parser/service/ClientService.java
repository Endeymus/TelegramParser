package com.endeymus.parser.service;

import com.endeymus.parser.entity.Client;

import java.util.List;

/**
 * @author Mark Shamray
 */
public interface ClientService {
    List<Client> findByIdInternal(Long id);
    List<Client> findByIdPhone(String phone);

}
