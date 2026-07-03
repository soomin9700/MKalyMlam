package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"methodeComptable\"")
public class MethodeComptable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idMethodeComptable\"")
    private Integer idMethodeComptable;
    
    @Column(name = "\"libelle\"")
    private String libelle;
    
    public MethodeComptable() {
    }
    
    public MethodeComptable(Integer idMethodeComptable, String libelle) {
        this.idMethodeComptable = idMethodeComptable;
        this.libelle = libelle;
    }
    
    public Integer getIdMethodeComptable() {
        return idMethodeComptable;
    }
    
    public void setIdMethodeComptable(Integer idMethodeComptable) {
        this.idMethodeComptable = idMethodeComptable;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}