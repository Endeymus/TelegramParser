package com.endeymus.parser.service;

import com.endeymus.parser.entity.Client;
import com.endeymus.parser.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Service("clientService")
@Transactional
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional(readOnly = true)
    public List<Client> findByIdInternal(Long id) {
        return em.createNamedQuery("Client.findByIdInternal", Client.class)
                .setParameter("id_internal", id)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findByIdPhone(String phone) {
        return null;
    }


}
