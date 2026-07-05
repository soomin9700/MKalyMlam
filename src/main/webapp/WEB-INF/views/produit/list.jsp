<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des produits - Administration</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_liste_prod.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_badge.css">
    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

<div class="dashboard">
    <c:set var="activeMenu" value="produits"/>

    <!-- Sidebar -->

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <!-- En-tête -->

            <div class="table-header">

                <h1>

                    <i class="fas fa-box"
                       style="color:var(--primary);margin-right:10px;"></i>

                    Liste des produits

                </h1>

                <a href="${pageContext.request.contextPath}/produits/new"
                   class="btn-add">

                    Ajouter un produit

                </a>

            </div>

            <!-- Filtres -->
            <div class="filters-container">
                <form action="${pageContext.request.contextPath}/produits" method="get" class="filters-form">
                    
                    <!-- Filtre par nom d'ingrédient -->
                    <div class="filter-group">
                        <label for="nomProduit" class="filter-label">
                            <i class="fas fa-search"></i>
                            Rechercher
                        </label>
                        <input 
                            type="text" 
                            id="nomIngredient" 
                            name="nomIngredient" 
                            value="${param.nomIngredient}" 
                            placeholder="Nom de l'ingrédient..."
                            class="filter-input">
                    </div>
                    
                    <!-- Filtre par nouveaute -->
                    <div class="filter-group filter-checkbox">
                        <label for="nouveauProduit" class="filter-label">
                            <i class="fas fa-star"></i>
                            Nouveau
                        </label>
                        <input 
                            type="checkbox" 
                            id="nouveauProduit" 
                            name="nouveauProduit" 
                            value="true"
                            ${param.nouveauProduit != null ? 'checked' : ''}
                            class="filter-checkbox">
                        <span class="filter-checkbox-label">Afficher uniquement les nouveaux poduits</span>
                    </div>
                    
                    <!-- Boutons d'action -->
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i>
                            Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/produits" class="btn-filter-reset">
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

                    <!-- <th><i class="fas fa-hashtag"></i> ID</th> -->

                    <th><i class="fas fa-tag"></i> Nom</th>

                    <th><i class="fas fa-euro-sign"></i> Prix</th>

                    <th><i class="fas fa-star"></i> Nouveau</th>

                    <th><i class="fas fa-calendar"></i> Date</th>

                    <th><i class="fas fa-cog"></i> Actions</th>

                </tr>

                </thead>

                <tbody>

                <!-- Aucun produit -->

                <c:if test="${empty produits}">

                    <tr>

                        <td colspan="6">

                            <div class="empty-state">

                                <i class="fas fa-box"
                                   style="font-size:48px;
                                          color:#d1d5db;
                                          margin-bottom:15px;
                                          display:block;"></i>

                                <p>
                                    Aucun produit enregistré pour le moment.
                                </p>

                                <a href="${pageContext.request.contextPath}/produits/new"
                                   class="btn-add">

                                    <i class="fas fa-plus"></i>

                                    Ajouter le premier produit

                                </a>

                            </div>

                        </td>

                    </tr>

                </c:if>

                <!-- Liste -->

                <c:forEach var="produit" items="${produits}">

                    <tr>

                        <!-- <td>

                            <span class="product-id">

                                ${produit.idProduit}

                            </span>

                        </td> -->

                        <td>

                            <strong>

                                ${produit.nomProduit}

                            </strong>

                        </td>

                        <td>

                            <span class="price-tag">

                                € ${produit.prixBase}

                            </span>

                        </td>

                        <td>

                            <c:choose>

                                <c:when test="${produit.estNouveau}">

                                    <span class="badge-new active">

                                        <i class="fas fa-check-circle"></i>

                                        Oui

                                    </span>

                                </c:when>

                                <c:otherwise>

                                    <span class="badge-new inactive">

                                        <i class="fas fa-times-circle"></i>

                                        Non

                                    </span>

                                </c:otherwise>

                            </c:choose>

                        </td>

                        <td>

                            ${produit.dateCreation}

                        </td>

                        <td>

                            <div class="table-actions">

                                <!-- Modifier -->

                                <a href="${pageContext.request.contextPath}/produits/${produit.idProduit}/edit"
                                   class="btn-edit">

                                    <i class="fas fa-edit"></i>

                                    Modifier

                                </a>

                                <!-- Supprimer -->

                                <form action="${pageContext.request.contextPath}/produits/${produit.idProduit}/delete"
                                      method="post"
                                      style="display:inline;"
                                      onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer ce produit ? Cette action est irréversible.');">

                                    <button type="submit"
                                            class="btn-delete">

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

        </div>

    </div>

</div>

</body>
</html>