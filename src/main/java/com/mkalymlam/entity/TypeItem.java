package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"typeItem\"")
public class TypeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idTypeItem\"")
    private Long idTypeItem;

    @Column(name = "\"libelle\"")
    private String libelle;

    public TypeItem(Long idTypeItem, String libelle) {
        this.idTypeItem = idTypeItem;
        this.libelle = libelle;
    }

    public TypeItem() {
    }

    public Long getIdTypeItem() {
        return idTypeItem;
    }

    public void setIdTypeItem(Long idTypeItem) {
        this.idTypeItem = idTypeItem;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
}
