<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des stocks</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="stocks"/>
    
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <div class="table-container">
                <a href="${pageContext.request.contextPath}/stocks" class="back-link">
                    <i class="fas fa-arrow-left"></i>
                    Retour au dashboard
                </a>

            <!-- En-tête -->
            <div class="table-header">
                <h1>
                    <i class="fas fa-boxes" style="color: var(--primary); margin-right: 10px;"></i>
                    Stock des ingrédients
                </h1>
                
                
            </div>

            <!-- Filtres -->
            <div class="filters-container">
                <form action="${pageContext.request.contextPath}/stocks/list" method="get" class="filters-form">
                    
                    <!-- Filtre par nom -->
                    <div class="filter-group">
                        <label for="nomIngredient" class="filter-label">
                            <i class="fas fa-search"></i>
                            Rechercher
                        </label>
                        <input 
                            type="text" 
                            id="nomIngredient" 
                            name="nomIngredient" 
                            value="${nomIngredient}" 
                            placeholder="Nom de l'ingrédient..."
                            class="filter-input">
                    </div>

                    <!-- Filtre par statut -->
                    <div class="filter-group">
                        <label for="statut" class="filter-label">
                            <i class="fas fa-filter"></i>
                            Statut
                        </label>
                        <select id="statut" name="statut" class="filter-input">
                            <option value="">Tous</option>
                            <option value="DISPONIBLE" ${statut == 'DISPONIBLE' ? 'selected' : ''}>Disponible</option>
                            <option value="ALERTE" ${statut == 'ALERTE' ? 'selected' : ''}>Seuil d'alerte</option>
                            <option value="RUPTURE" ${statut == 'RUPTURE' ? 'selected' : ''}>Rupture</option>
                        </select>
                    </div>

                    <!-- Boutons -->
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i>
                            Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/stocks/list" class="btn-filter-reset">
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
                        <th>Ingrédient</th>
                        <th>Unité</th>
                        <th>Seuil d'alerte</th>
                        <th>Quantité totale</th>
                        <th>Valeur</th>
                        <th>Statut</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty ingredients}">
                            <tr>
                                <td colspan="7">
                                    <div class="empty-state">
                                        <i class="fas fa-boxes" style="font-size: 48px; color: #d1d5db; margin-bottom: 15px; display: block;"></i>
                                        <p>Aucun ingrédient trouvé</p>
                                    </div>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${ingredients}" var="ingredient">
                                <tr>
                                    <td>
                                        <strong>${ingredient.nomIngredient}</strong>
                                    </td>
                                    <td>${ingredient.uniteMesure}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty ingredient.seuilAlerte}">
                                                ${ingredient.seuilAlerte}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <span class="${ingredient.quantiteTotale == 0 ? 'text-danger' : ''}">
                                            ${ingredient.quantiteTotale}
                                        </span>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${ingredient.valeurTotale}" type="currency" currencySymbol="€" maxFractionDigits="2"/>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${ingredient.statut == 'DISPONIBLE'}">
                                                <span class="badge bg-success">
                                                    <i class="fas fa-check-circle"></i> Disponible
                                                </span>
                                            </c:when>
                                            <c:when test="${ingredient.statut == 'ALERTE'}">
                                                <span class="badge bg-warning text-dark">
                                                    <i class="fas fa-exclamation-triangle"></i> Alerte
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">
                                                    <i class="fas fa-times-circle"></i> Rupture
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/stocks/detail?id=${ingredient.idIngredient}" class="btn-edit">
                                            <i class="fas fa-eye"></i>
                                            Détail
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>