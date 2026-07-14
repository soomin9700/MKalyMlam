<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mouvements de stock</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="mouvements"/>
    
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <div class="table-container">

            <!-- En-tête -->
            <div class="table-header">
                <h1>
                    <i class="fas fa-exchange-alt" style="color: var(--primary); margin-right: 10px;"></i>
                    ${empty titre ? 'Mouvements de stock' : titre}
                </h1>
                <div style="display: flex; gap: 10px;">
                    <a href="${pageContext.request.contextPath}/mouvements/jour" class="btn-add" style="background: #1976d2;">
                        <i class="fas fa-calendar-day"></i>
                        Aujourd'hui
                    </a>
                    <a href="${pageContext.request.contextPath}/stocks" class="btn-add">
                        <i class="fas fa-arrow-left"></i>
                        Retour
                    </a>
                </div>
            </div>

            <!-- Statistiques rapides -->
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 15px; margin-bottom: 20px;">
                <div style="background: #e3f2fd; padding: 15px; border-radius: 8px; text-align: center;">
                    <span style="font-size: 12px; color: #1976d2;">Total</span>
                    <div style="font-size: 24px; font-weight: 700; color: #1976d2;">${totalMouvements}</div>
                </div>
                <div style="background: #e8f5e9; padding: 15px; border-radius: 8px; text-align: center;">
                    <span style="font-size: 12px; color: #388e3c;">Entrées</span>
                    <div style="font-size: 24px; font-weight: 700; color: #388e3c;">${totalEntrees}</div>
                </div>
                <div style="background: #fce4ec; padding: 15px; border-radius: 8px; text-align: center;">
                    <span style="font-size: 12px; color: #c62828;">Sorties</span>
                    <div style="font-size: 24px; font-weight: 700; color: #c62828;">${totalSorties}</div>
                </div>
                <div style="background: #fff3e0; padding: 15px; border-radius: 8px; text-align: center;">
                    <span style="font-size: 12px; color: #e65100;">Ajustements</span>
                    <div style="font-size: 24px; font-weight: 700; color: #e65100;">${totalAjustements}</div>
                </div>
            </div>

            <!-- Filtres -->
            <div class="filters-container">
                <form action="${pageContext.request.contextPath}/mouvements" method="get" class="filters-form">
                    
                    <!-- Date début -->
                    <div class="filter-group">
                        <label for="dateDebut" class="filter-label">
                            <i class="fas fa-calendar-alt"></i>
                            Date début
                        </label>
                        <input 
                            type="date" 
                            id="dateDebut" 
                            name="dateDebut" 
                            value="${dateDebut}"
                            class="filter-input">
                    </div>

                    <!-- Date fin -->
                    <div class="filter-group">
                        <label for="dateFin" class="filter-label">
                            <i class="fas fa-calendar-alt"></i>
                            Date fin
                        </label>
                        <input 
                            type="date" 
                            id="dateFin" 
                            name="dateFin" 
                            value="${dateFin}"
                            class="filter-input">
                    </div>

                    <!-- Type de mouvement -->
                    <div class="filter-group">
                        <label for="typeMouvement" class="filter-label">
                            <i class="fas fa-tag"></i>
                            Type
                        </label>
                        <select id="typeMouvement" name="typeMouvement" class="filter-input">
                            <option value="">Tous</option>
                            <option value="ENTREE" ${typeMouvement == 'ENTREE' ? 'selected' : ''}>Entrée</option>
                            <option value="SORTIE" ${typeMouvement == 'SORTIE' ? 'selected' : ''}>Sortie</option>
                            <option value="AJUSTEMENT" ${typeMouvement == 'AJUSTEMENT' ? 'selected' : ''}>Ajustement</option>
                        </select>
                    </div>

                    <!-- Ingrédient -->
                    <div class="filter-group">
                        <label for="idIngredient" class="filter-label">
                            <i class="fas fa-utensils"></i>
                            Ingrédient
                        </label>
                        <select id="idIngredient" name="idIngredient" class="filter-input">
                            <option value="">Tous</option>
                            <c:forEach items="${ingredients}" var="ingredient">
                                <option value="${ingredient.idIngredient}" ${idIngredient == ingredient.idIngredient ? 'selected' : ''}>
                                    ${ingredient.nomIngredient}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Boutons -->
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i>
                            Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/mouvements" class="btn-filter-reset">
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
                        <th>Date</th>
                        <th>Ingrédient</th>
                        <th>Type</th>
                        <th>Quantité</th>
                        <th>Avant</th>
                        <th>Après</th>
                        <th>Motif</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty mouvements}">
                            <tr>
                                <td colspan="7">
                                    <div class="empty-state">
                                        <i class="fas fa-exchange-alt" style="font-size: 48px; color: #d1d5db; margin-bottom: 15px; display: block;"></i>
                                        <p>Aucun mouvement de stock trouvé</p>
                                    </div>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${mouvements}" var="mvt">
                                <tr>
                                    <td>
                                        <fmt:formatDate value="${mvt.dateMouvement}" pattern="dd/MM/yyyy HH:mm"/>
                                    </td>
                                    <td>
                                        <strong>${mvt.nomIngredient}</strong>
                                        <br>
                                        <small style="color: #6b7280;">Lot #${mvt.nomLot}</small>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${mvt.typeMouvement == 'ENTREE'}">
                                                <span class="badge bg-success">
                                                    <i class="fas fa-arrow-down"></i> Entrée
                                                </span>
                                            </c:when>
                                            <c:when test="${mvt.typeMouvement == 'SORTIE'}">
                                                <span class="badge bg-danger">
                                                    <i class="fas fa-arrow-up"></i> Sortie
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">
                                                    <i class="fas fa-pen"></i> Ajustement
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <span class="${mvt.typeMouvement == 'ENTREE' ? 'text-success' : 'text-danger'}">
                                            ${mvt.typeMouvement == 'ENTREE' ? '+' : '-'}
                                            ${mvt.quantite} ${mvt.uniteMesure}
                                        </span>
                                    </td>
                                    <td>${mvt.quantiteAvant} ${mvt.uniteMesure}</td>
                                    <td>${mvt.quantiteApres} ${mvt.uniteMesure}</td>
                                    <td>
                                        <span style="font-size: 13px; color: #6b7280;">
                                            ${not empty mvt.motif ? mvt.motif : '-'}
                                        </span>
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