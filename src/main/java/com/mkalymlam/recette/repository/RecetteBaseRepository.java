package com.mkalymlam.recette.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mkalymlam.recette.model.RecetteBase;


@Repository
public interface RecetteBaseRepository extends JpaRepository<RecetteBase, Long> {
    // List<RecetteBase> findByIdProduit(Long id_produit);
    // List<RecetteBase> findByIdIngredient(Long id_ingredient);


}