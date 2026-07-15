<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des recettes - Administration</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_badge.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="recettes"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <!-- En-tête -->
            <div class="table-header">
                <h1>
                    <i class="fas fa-utensils" style="color: var(--primary); margin-right:10px;"></i>
                    Liste des recettes
                </h1>
                <a href="${pageContext.request.contextPath}/recetteBase/new" class="btn-add">
                    Ajouter une recette
                </a>

                <a href="${pageContext.request.contextPath}/recetteBase/import"
                   class="btn-add" style="background:#6366f1;">
                    <i class="fas fa-file-import"></i> Importer CSV/Excel
                </a>

            </div>

            <!-- Statistiques -->
            <div style="display: flex; gap: 20px; margin-bottom: 20px; flex-wrap: wrap;">
                <div style="background: #e3f2fd; padding: 10px 20px; border-radius: 8px;">
                    <span style="font-size: 12px; color: #1976d2;">Total recettes</span>
                    <div style="font-size: 20px; font-weight: 700; color: #1976d2;">${totalRecettes}</div>
                </div>
                <div style="background: #e8f5e9; padding: 10px 20px; border-radius: 8px;">
                    <span style="font-size: 12px; color: #388e3c;">Produits</span>
                    <div style="font-size: 20px; font-weight: 700; color: #388e3c;">${produits.size()}</div>
                </div>
                <div style="background: #fff3e0; padding: 10px 20px; border-radius: 8px;">
                    <span style="font-size: 12px; color: #e65100;">Ingrédients</span>
                    <div style="font-size: 20px; font-weight: 700; color: #e65100;">${ingredients.size()}</div>
                </div>
            </div>

            <!-- Filtres -->
            <div class="filters-container">
                <form action="${pageContext.request.contextPath}/recetteBase" method="get" class="filters-form">
                    
                    <!-- Filtre par nom de produit -->
                    <div class="filter-group">
                        <label for="nomProduit" class="filter-label">
                            <i class="fas fa-box"></i>
                            Produit
                        </label>
                        <input 
                            type="text" 
                            id="nomProduit" 
                            name="nomProduit" 
                            value="${nomProduit}" 
                            placeholder="Nom du produit..."
                            class="filter-input">
                    </div>

                    <!-- Filtre par nom d'ingrédient -->
                    <div class="filter-group">
                        <label for="nomIngredient" class="filter-label">
                            <i class="fas fa-carrot"></i>
                            Ingrédient
                        </label>
                        <input 
                            type="text" 
                            id="nomIngredient" 
                            name="nomIngredient" 
                            value="${nomIngredient}" 
                            placeholder="Nom de l'ingrédient..."
                            class="filter-input">
                    </div>

                    <!-- Boutons d'action -->
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i>
                            Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/recetteBase" class="btn-filter-reset">
                            <i class="fas fa-undo"></i>
                            Réinitialiser
                        </a>
                    </div>
                    
                </form>
            </div>

            <!-- Tableau -->
            <table>
                <thead>
                    <tr>
                        <th><i class="fas fa-box"></i> Produit</th>
                        <th><i class="fas fa-carrot"></i> Ingrédient</th>
                        <th><i class="fas fa-weight-hanging"></i> Quantité</th>
                        <th><i class="fas fa-cog"></i> Actions</th>
                    </tr>
                </thead>

                <tbody>
                    <!-- Aucune recette -->
                    <c:if test="${empty recettes}">
                        <tr>
                            <td colspan="4">
                                <div class="empty-state">
                                    <i class="fas fa-utensils" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                    <p>Aucune recette trouvée.</p>
                                    <c:if test="${not empty nomProduit or not empty nomIngredient}">
                                        <p style="font-size: 14px; color: #6b7280;">
                                            <i class="fas fa-info-circle"></i>
                                            Essayez de modifier vos critères de recherche.
                                        </p>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/recetteBase/new" class="btn-add">
                                        <i class="fas fa-plus"></i>
                                        Ajouter une recette
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:if>

                    <!-- Liste des recettes -->
                    <c:forEach items="${recettes}" var="recette">
                        <tr>
                            <!-- Produit -->
                            <td>
                                <c:forEach items="${produits}" var="produit">
                                    <c:if test="${produit.idProduit == recette.idProduit}">
                                        <strong>${produit.nomProduit}</strong>
                                    </c:if>
                                </c:forEach>
                            </td>

                            <!-- Ingrédient -->
                            <td>
                                <c:forEach items="${ingredients}" var="ingredient">
                                    <c:if test="${ingredient.idIngredient == recette.idIngredient}">
                                        <span style="display: flex; align-items: center; gap: 8px;">
                                            <i class="fas fa-circle" style="color: var(--primary); font-size: 8px;"></i>
                                            ${ingredient.nomIngredient}
                                        </span>
                                    </c:if>
                                </c:forEach>
                            </td>

                            <!-- Quantité -->
                            <td>
                                <span class="badge bg-info text-dark" style="font-size: 14px; padding: 6px 14px;">
                                    <i class="fas fa-weight-hanging"></i>
                                    ${recette.quantiteRecette}
                                </span>
                            </td>

                            <!-- Actions -->
                            <td>
                                <div class="table-actions">
                                    <!-- Modifier -->
                                    <a href="${pageContext.request.contextPath}/recetteBase/${recette.idProduit}/${recette.idIngredient}/edit"
                                       class="btn-edit">
                                        <i class="fas fa-edit"></i>
                                        Modifier
                                    </a>

                                    <!-- Supprimer -->
                                    <form action="${pageContext.request.contextPath}/recetteBase/${recette.idProduit}/${recette.idIngredient}/delete"
                                          method="post"
                                          style="display:inline;"
                                          onsubmit="return confirm('Supprimer cette recette ?');">
                                        <button type="submit" class="btn-delete">
                                            <i class="fas fa-trash-alt"></i>
                                            Supprimer
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Pagination / informations -->
            <div style="margin-top: 20px; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px;">
                <span style="color: #6b7280; font-size: 14px;">
                    <i class="fas fa-info-circle"></i>
                    ${recettes.size()} recette(s) trouvée(s)
                </span>
                <c:if test="${not empty nomProduit or not empty nomIngredient}">
                    <span style="color: #6b7280; font-size: 14px;">
                        <i class="fas fa-filter"></i>
                        Filtres appliqués :
                        <c:if test="${not empty nomProduit}">
                            <span class="badge bg-primary">Produit: ${nomProduit}</span>
                        </c:if>
                        <c:if test="${not empty nomIngredient}">
                            <span class="badge bg-success">Ingrédient: ${nomIngredient}</span>
                        </c:if>
                    </span>
                </c:if>
            </div>

        </div>

    </div>

</div>

</body>
</html>