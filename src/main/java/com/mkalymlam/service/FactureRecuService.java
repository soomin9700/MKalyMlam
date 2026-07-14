package com.mkalymlam.service;

import com.mkalymlam.entity.FactureRecu;
import com.mkalymlam.repository.FactureRecuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactureRecuService {

    private final FactureRecuRepository factureRecuRepository;

    public FactureRecuService(FactureRecuRepository factureRecuRepository) {
        this.factureRecuRepository = factureRecuRepository;
    }

    public List<FactureRecu> listerToutes() {
        return factureRecuRepository.findAllByOrderByDateFacturationDesc();
    }
}
