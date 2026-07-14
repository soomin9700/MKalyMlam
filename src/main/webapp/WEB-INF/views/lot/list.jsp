<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des lots</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_badge.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <!-- En-tête -->
            <div class="table-header">

                <h1>
                    <i class="fas fa-utensils"
                       style="color: var(--primary); margin-right:10px;"></i>
                    Liste des lots d'ingrédients
                </h1>

                <a href="#lot-insert-form"
                   class="btn-add">
                    Ajouter un nouveau lot
                </a>

            </div>

            <c:if test="${not empty successMessage}">
                <div class="alert-success">
                    <i class="fas fa-check-circle"></i>
                    ${successMessage}
                </div>
            </c:if>

            <div class="lot-insert-section" id="lot-insert-form">
                <div class="lot-insert-header">
                    <h2><i class="fas fa-plus-circle"></i> Ajouter un lot</h2>
                    <p>Renseignez les informations du nouveau lot avant de consulter la liste.</p>
                </div>

                <form action="${pageContext.request.contextPath}/lot/save" method="post" class="lot-inline-form">
                    <div class="form-group">
                        <label for="ingredientSelect">Ingrédient <span class="required-star">*</span></label>
                        <select id="ingredientSelect" name="ingredient.idIngredient" required>
                            <option value="">-- Choisir un ingrédient --</option>
                            <c:forEach items="${ingredients}" var="ingredient">
                                <option value="${ingredient.idIngredient}">${ingredient.nomIngredient}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="quantiteInitiale">Quantité reçue <span class="required-star">*</span></label>
                            <input type="number" id="quantiteInitiale" name="quantiteInitiale" step="0.01" min="0" required placeholder="Ex: 5">
                        </div>

                        <div class="form-group">
                            <label for="prixAchatUnitaire">Prix d'achat unitaire</label>
                            <input type="number" id="prixAchatUnitaire" name="prixAchatUnitaire" step="0.01" min="0" placeholder="Ex: 5000">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="dateReception">Date de réception</label>
                            <input type="date" id="dateReception" name="dateReception">
                        </div>

                        <div class="form-group">
                            <label for="datePeremption">Date de péremption</label>
                            <input type="date" id="datePeremption" name="datePeremption">
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn-success">
                            <i class="fas fa-save"></i>
                            Enregistrer le lot
                        </button>
                        <button type="reset" class="btn-secondary">
                            <i class="fas fa-undo"></i>
                            Réinitialiser
                        </button>
                    </div>
                </form>
            </div>

            <!-- Filtres -->
            <div class="filters-container">
                <form action="${pageContext.request.contextPath}/lot/findAll" method="get" class="filters-form">
                    
                    <!-- Filtre par nom d'ingrédient -->
                    <div class="filter-group">
                        <label for="nomIngredient" class="filter-label">
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
                    
                    <!-- Filtre par alerte -->
                    <div class="filter-group filter-checkbox">
                        <label for="alerte" class="filter-label">
                            <i class="fas fa-exclamation-triangle"></i>
                            Alerte
                        </label>
                        <input 
                            type="checkbox" 
                            id="alerte" 
                            name="alerte" 
                            value="true"
                            ${param.alerte != null ? 'checked' : ''}
                            class="filter-checkbox">
                        <span class="filter-checkbox-label">Afficher uniquement les lots en alerte</span>
                    </div>
                    
                    <!-- Boutons d'action -->
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i>
                            Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/lot/findAll" class="btn-filter-reset">
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
                        <th><i class="fas fa-tag"></i> Ingrédient</th>
                        <th><i class="fas fa-calendar-plus"></i> Date de réception</th>
                        <th><i class="fas fa-calendar-times"></i> Date de péremption</th>
                        <th><i class="fas fa-weight"></i> Qtt initiale</th>
                        <th><i class="fas fa-weight-hanging"></i> Qtt restante</th>
                        <th><i class="fas fa-euro-sign"></i> Prix d'achat unitaire</th>
                        <th><i class="fas fa-info-circle"></i> Statut</th>
                        <th><i class="fas fa-cog"></i> Actions</th>
                    </tr>

                </thead>

                <tbody>

                    <!-- Aucun lot -->
                    <c:if test="${empty lots}">
                        <tr>

                            <td colspan="8">

                                <div class="empty-state">

                                    <i class="fas fa-boxes"
                                       style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>

                                    <p>Aucun lot d'ingrédient enregistré pour le moment.</p>

                                    <a href="${pageContext.request.contextPath}/lot/save"
                                       class="btn-add">

                                        <i class="fas fa-plus"></i>
                                        Ajouter le premier lot

                                    </a>

                                </div>

                            </td>

                        </tr>
                    </c:if>

                    <!-- Liste des lots -->
                    <c:forEach items="${lots}" var="lot">

                        <tr>

                            <td>
                                <strong>${lot.ingredient != null && lot.ingredient.nomIngredient != null ? lot.ingredient.nomIngredient : '-'}</strong>
                            </td>

                            <td>
                                    ${lot.dateReception}
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${lot.datePeremption != null}">
                                
                                            ${lot.datePeremption}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">-</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <span class="">
                                    ${lot.quantiteInitiale != null ? lot.quantiteInitiale : 0}
                                </span>
                            </td>

                            <td>
                                <span class="">
                                    ${quantitesRestantes[lot.idLot] != null ? quantitesRestantes[lot.idLot] : 0}
                                </span>
                            </td>

                            <td>
                                <span class="price-tag">
                                    ${lot.prixAchatUnitaire != null ? lot.prixAchatUnitaire : 0} €
                                </span>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${quantitesRestantes[lot.idLot] != null && quantitesRestantes[lot.idLot] == 0}">
                                        <span class="badge bg-danger">
                                            <i class="fas fa-times-circle"></i>
                                            Épuisé
                                        </span>
                                    </c:when>
                                    <c:when test="${lot.ingredient != null && lot.ingredient.seuilAlerteQuantite != null && quantitesRestantes[lot.idLot] != null && quantitesRestantes[lot.idLot] <= lot.ingredient.seuilAlerteQuantite}">
                                        <span class="badge bg-warning text-dark">
                                            <i class="fas fa-exclamation-triangle"></i>
                                            ALERTE
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-success">
                                            <i class="fas fa-check-circle"></i>
                                            OK
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>

                                <div class="actions">

                                    <!-- Modifier -->
                                    <a href="${pageContext.request.contextPath}/lot/update/${lot.idLot != null ? lot.idLot : ''}"
                                    class="btn-edit">

                                        <i class="fas fa-edit"></i>
                                        Modifier

                                    </a>

                                    <!-- Supprimer -->
                                    <form action="${pageContext.request.contextPath}/lot/delete/${lot.idLot != null ? lot.idLot : ''}"
                                          method="post"
                                          style="display:inline;"
                                          onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer ce lot ?');">

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