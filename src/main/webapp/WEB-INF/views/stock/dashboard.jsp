<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard des stocks</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="stocks"/>
    
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <div class="page-header">
            <h1>
                <i class="fas fa-boxes" style="color: var(--primary); margin-right: 12px;"></i>
                Gestion des stocks
            </h1>
            <p class="subtitle">Vue d'ensemble des stocks d'ingrédients</p>
        </div>

        <!-- KPI Cards -->
        <div class="kpi-grid">
            <!-- Total ingrédients -->
            <div class="kpi-card">
                <div class="kpi-icon" style="background: #e3f2fd; color: #1976d2;">
                    <i class="fas fa-utensils"></i>
                </div>
                <div class="kpi-content">
                    <span class="kpi-label">Total ingrédients</span>
                    <span class="kpi-value">${stats.totalIngredients}</span>
                </div>
            </div>

            <!-- Disponibles -->
            <div class="kpi-card">
                <div class="kpi-icon" style="background: #e8f5e9; color: #388e3c;">
                    <i class="fas fa-check-circle"></i>
                </div>
                <div class="kpi-content">
                    <span class="kpi-label">Disponibles</span>
                    <span class="kpi-value">${stats.ingredientsDisponibles}</span>
                    <span class="kpi-sub">
                        ${stats.ingredientsDisponibles / stats.totalIngredients * 100}%
                    </span>
                </div>
            </div>

            <!-- Rupture -->
            <div class="kpi-card">
                <div class="kpi-icon" style="background: #fce4ec; color: #c62828;">
                    <i class="fas fa-times-circle"></i>
                </div>
                <div class="kpi-content">
                    <span class="kpi-label">Rupture de stock</span>
                    <span class="kpi-value">${stats.ingredientsEnRupture}</span>
                    <span class="kpi-sub danger">
                        ${stats.ingredientsEnRupture / stats.totalIngredients * 100}%
                    </span>
                </div>
            </div>

            <!-- Alerte -->
            <div class="kpi-card">
                <div class="kpi-icon" style="background: #fff3e0; color: #e65100;">
                    <i class="fas fa-exclamation-triangle"></i>
                </div>
                <div class="kpi-content">
                    <span class="kpi-label">Seuil d'alerte</span>
                    <span class="kpi-value">${stats.ingredientsEnAlerte}</span>
                    <span class="kpi-sub warning">
                        ${stats.ingredientsEnAlerte / stats.totalIngredients * 100}%
                    </span>
                </div>
            </div>

            <!-- Valeur totale -->
            <div class="kpi-card">
                <div class="kpi-icon" style="background: #e8f5e9; color: #2e7d32;">
                    <i class="fas fa-euro-sign"></i>
                </div>
                <div class="kpi-content">
                    <span class="kpi-label">Valeur totale du stock</span>
                    <span class="kpi-value">
                        <fmt:formatNumber value="${stats.valeurTotaleStock}" type="currency" currencySymbol="€" maxFractionDigits="2"/>
                    </span>
                </div>
            </div>
        </div>

        <!-- Actions rapides -->
        <div class="quick-actions">
            <a href="${pageContext.request.contextPath}/stocks/list" class="btn-primary">
                <i class="fas fa-list"></i>
                Voir tous les stocks
            </a>
            <a href="${pageContext.request.contextPath}/stocks/list?statut=DISPONIBLE" class="btn-success">
                <i class="fas fa-check"></i>
                Ingrédients disponibles
            </a>
            <a href="${pageContext.request.contextPath}/stocks/list?statut=ALERTE" class="btn-warning">
                <i class="fas fa-exclamation-triangle"></i>
                Seuil d'alerte
            </a>
            <a href="${pageContext.request.contextPath}/stocks/list?statut=RUPTURE" class="btn-danger">
                <i class="fas fa-times"></i>
                Rupture de stock
            </a>
        </div>

        <!-- Top 5 des ingrédients -->
        <div class="table-container" style="margin-top: 30px;">
            <div class="table-header">
                <h2>
                    <i class="fas fa-chart-simple" style="color: var(--primary); margin-right: 10px;"></i>
                    Aperçu des stocks
                </h2>
                <a href="${pageContext.request.contextPath}/stocks/list" class="btn-add">
                    Voir tout <i class="fas fa-arrow-right"></i>
                </a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Ingrédient</th>
                        <th>Unité</th>
                        <th>Quantité totale</th>
                        <th>Valeur</th>
                        <th>Statut</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty topIngredients}">
                            <tr>
                                <td colspan="6">
                                    <div class="empty-state">
                                        <i class="fas fa-boxes" style="font-size: 48px; color: #d1d5db; margin-bottom: 15px; display: block;"></i>
                                        <p>Aucun ingrédient en stock</p>
                                    </div>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${topIngredients}" var="ingredient">
                                <tr>
                                    <td>
                                        <strong>${ingredient.nomIngredient}</strong>
                                    </td>
                                    <td>${ingredient.uniteMesure}</td>
                                    <td>${ingredient.quantiteTotale}</td>
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
                                        <a href="${pageContext.request.contextPath}/stocks/detail?id=${ingredient.idIngredient}" class="btn-edit btn-sm">
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