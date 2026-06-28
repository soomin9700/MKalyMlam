package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "personnalisation_commande")
public class PersonnalisationCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpersonnalisation")
    private Long idPersonnalisation;

    @Column(name = "idline")
    private Long idLine; // Correspond à idLine de LigneCommande

    @Column(name = "idingredient")
    private Integer idIngredient;

    @Column(name = "idactioncommande")
    private Integer idActionCommande;

    @Column(name = "quantiteajustee")
    private Double quantiteAjustee;

    public PersonnalisationCommande() {}

    // Getters et Setters
    public Long getIdPersonnalisation() { return idPersonnalisation; }
    public void setIdPersonnalisation(Long idPersonnalisation) { this.idPersonnalisation = idPersonnalisation; }

    public Long getIdLine() { return idLine; }
    public void setIdLine(Long idLine) { this.idLine = idLine; }

    public Integer getIdIngredient() { return idIngredient; }
    public void setIdIngredient(Integer idIngredient) { this.idIngredient = idIngredient; }

    public Integer getIdActionCommande() { return idActionCommande; }
    public void setIdActionCommande(Integer idActionCommande) { this.idActionCommande = idActionCommande; }

    public Double getQuantiteAjustee() { return quantiteAjustee; }
    public void setQuantiteAjustee(Double quantiteAjustee) { this.quantiteAjustee = quantiteAjustee; }
}