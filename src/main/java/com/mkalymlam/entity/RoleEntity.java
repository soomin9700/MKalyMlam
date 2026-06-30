// Fichier RoleEntity.java
package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idRole\"")
    private Long idRole;

    @Column(name = "\"libelle\"", nullable = false, length = 50)
    private String libelle;

    // getters & setters (obligatoires)
    public Long getIdRole() { return idRole; }
    public void setIdRole(Long idRole) { this.idRole = idRole; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}