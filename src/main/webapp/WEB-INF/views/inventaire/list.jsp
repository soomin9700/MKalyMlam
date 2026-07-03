<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Inventaires</title>

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
                    <i class="fas fa-clipboard-list"
                       style="color: var(--primary); margin-right:10px;"></i>
                    ${titre}
                </h1>

                <a href="${pageContext.request.contextPath}/inventaire/new"
                   class="btn-add">
                    Nouvel inventaire
                </a>

            </div>

            <!-- Filtres -->
            <div class="filters-container">
                <form action="${pageContext.request.contextPath}/inventaire/findAll" method="get" class="filters-form">
                    
                    <!-- Filtre par session -->
                    <div class="filter-group">
                        <label for="idSession" class="filter-label">
                            <i class="fas fa-truck"></i>
                            Session
                        </label>
                        <select id="idSession" name="idSession" class="filter-select">
                            <option value="">-- Toutes les sessions --</option>
                            <c:forEach items="${sessions}" var="session">
                                <option value="${session.id}"
                                    ${session.id == idSessionFiltre ? 'selected' : ''}>
                                    Session #${session.id} - ${session.dateSession}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Filtre par date -->
                    <div class="filter-group">
                        <label for="dateInventaire" class="filter-label">
                            <i class="fas fa-search"></i>
                            Rechercher par date
                        </label>
                        <input 
                            type="date" 
                            id="dateInventaire" 
                            name="dateInventaire" 
                            value="${dateInventaire}" 
                            class="filter-input">
                    </div>
                    
                    <!-- Filtre par écart -->
                    <div class="filter-group filter-checkbox">
                        <label for="ecart" class="filter-label">
                            <i class="fas fa-exclamation-triangle"></i>
                            Écarts
                        </label>
                        <input 
                            type="checkbox" 
                            id="ecart" 
                            name="ecart" 
                            value="true"
                            ${ecartFiltre != null && ecartFiltre ? 'checked' : ''}
                            class="filter-checkbox">
                        <span class="filter-checkbox-label">Afficher uniquement les inventaires avec écarts</span>
                    </div>
                    
                    <!-- Boutons d'action -->
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i>
                            Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/inventaire/findAll" class="btn-filter-reset">
                            <i class="fas fa-undo"></i>
                            Réinitialiser
                        </a>
                    </div>
                    
                </form>
            </div>

            <!-- Résumé des filtres actifs -->
            <c:if test="${filtreActif != 'tous'}">
                <div class="filter-summary">
                    <span class="filter-badge">
                        <i class="fas fa-filter"></i>
                        Filtre actif : 
                        <c:choose>
                            <c:when test="${filtreActif == 'ecart'}">
                                <strong>Inventaires avec écarts</strong>
                            </c:when>
                            <c:when test="${filtreActif == 'session'}">
                                <strong>Session #${idSessionFiltre}</strong>
                            </c:when>
                        </c:choose>
                    </span>
                    <span class="filter-count">
                        <i class="fas fa-list"></i>
                        ${inventaires.size()} inventaire(s) trouvé(s)
                    </span>
                </div>
            </c:if>

            <!-- Tableau -->
            <table>

                <thead>
                    <tr>
                        <th><i class="fas fa-tag"></i> Session</th>
                        <th><i class="fas fa-calendar"></i> Date inventaire</th>
                        <th><i class="fas fa-tag"></i> Type</th>
                        <th><i class="fas fa-box"></i> Item</th>  
                        <th><i class="fas fa-weight-hanging"></i> Quantité théorique</th>
                        <th><i class="fas fa-weight"></i> Quantité physique</th>
                        <th><i class="fas fa-balance-scale"></i> Écart</th>
                        <th><i class="fas fa-cog"></i> Actions</th>
                    </tr>
                </thead>

                <tbody>

                    <!-- Aucun inventaire -->
                    <c:if test="${empty inventaires}">
                        <tr>
                            <td colspan="8">
                                <div class="empty-state">
                                    <i class="fas fa-boxes"
                                       style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                    <p>Aucun inventaire trouvé pour les filtres sélectionnés.</p>
                                    <a href="${pageContext.request.contextPath}/inventaire/findAll" class="btn-filter-reset">
                                        <i class="fas fa-undo"></i>
                                        Réinitialiser les filtres
                                    </a>
                                    <br>
                                    <a href="${pageContext.request.contextPath}/inventaire/new" class="btn-add" style="margin-top:10px;">
                                        Ajouter un inventaire
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:if>

                    <!-- Liste des inventaires -->
                    <c:forEach items="${inventaires}" var="inventaire">

                        <tr>
                            <td>
                                <strong>Session #${inventaire.sessionTruck.id}</strong>
                                <br>
                                <small>${inventaire.sessionTruck.dateSession}</small>
                            </td>

                            <td>
                                ${inventaire.dateInventaire}
                            </td>

                            <td>
                                <span class="">
                                    ${inventaire.typeItem.libelle}
                                </span>
                            </td>

                            <td>
                                <span class="">
                                    <i class="fas fa-box"></i>
                                    ${inventaire.nomItem}
                                </span>
                            </td>

                            <td>
                                <span class="">
                                    ${inventaire.quantiteTheoriqueSysteme}
                                </span>
                            </td>

                            <td>
                                <span class="">
                                    ${inventaire.quantitePhysiqueConstatee}
                                </span>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${inventaire.ecartInventaire != 0}">
                                        <span class="badge bg-danger">
                                            <i class="fas fa-times-circle"></i>
                                            ${inventaire.ecartInventaire}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-success">
                                            <i class="fas fa-check-circle"></i>
                                            OK (0)
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <div class="actions">
                                    <a href="${pageContext.request.contextPath}/inventaire/edit/${inventaire.idInventaire}"
                                       class="btn-edit">
                                        <i class="fas fa-edit"></i>
                                        Modifier
                                    </a>

                                    <form action="${pageContext.request.contextPath}/inventaire/delete/${inventaire.idInventaire}"
                                          method="post"
                                          style="display:inline;"
                                          onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet inventaire ?');">
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

        </div>

    </div>

</div>

</body>
</html>