package com.mkalymlam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.repository.TypeMouvementRepository;

@Service
public class TypeMouvementService {
    
    @Autowired
    private TypeMouvementRepository typeMouvementRepository;

    public TypeMouvement getTypeMouvementByLibelle(String libelle) {
        TypeMouvement typeMouvement = typeMouvementRepository.findByLibelle(libelle);
        if (typeMouvement == null) {
            throw new RuntimeException("Type de mouvement non trouvé : " + libelle);
        }
        return typeMouvement;
    }
}
