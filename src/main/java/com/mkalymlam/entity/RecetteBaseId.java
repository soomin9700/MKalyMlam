package com.mkalymlam.entity;

import java.io.Serializable;
import java.util.Objects;

public class RecetteBaseId implements Serializable {

    private Long idProduit;
    private Long idIngredient;

    public RecetteBaseId() {
    }

    public RecetteBaseId(Long idProduit, Long idIngredient) {
        this.idProduit = idProduit;
        this.idIngredient = idIngredient;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public Long getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Long idIngredient) {
        this.idIngredient = idIngredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecetteBaseId)) return false;
        RecetteBaseId that = (RecetteBaseId) o;
        return Objects.equals(idProduit, that.idProduit) &&
               Objects.equals(idIngredient, that.idIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduit, idIngredient);
    }
}