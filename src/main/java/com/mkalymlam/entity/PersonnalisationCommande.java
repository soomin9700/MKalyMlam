package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"personnalisationCommande\"")
public class PersonnalisationCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idPersonnalisation\"")
    private Long idPersonnalisation;

    @Column(name = "\"idLigne\"")
    private Long idLine;

    @Column(name = "\"idIngredient\"")
    private Integer idIngredient;

    @Column(name = "\"idActionCommande\"")
    private Integer idActionCommande;

    @Column(name = "\"quantiteAjustee\"")
    private Double quantiteAjustee;

    public PersonnalisationCommande() {}

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
