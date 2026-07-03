package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"typeMouvement\"")
public class TypeMouvement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idTypeMouvement\"")
    private Long idTypeMouvement;   

    @Column(name = "\"libelle\"")
    private String libelle;

    public TypeMouvement(Long idTypeMouvement, String libelle) {
        this.idTypeMouvement = idTypeMouvement;
        this.libelle = libelle;
    }

    public TypeMouvement() {
    }

    public Long getIdTypeMouvement() {  
        return idTypeMouvement;
    }
    
    public void setIdTypeMouvement(Long idTypeMouvement) {  
        this.idTypeMouvement = idTypeMouvement;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}