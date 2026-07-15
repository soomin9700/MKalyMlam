<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Performances</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_badge.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="performances"/>
    
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <!-- En-tête -->
            <div class="table-header">
                <h1>
                    <i class="fas fa-chart-line" style="color: var(--primary); margin-right:10px;"></i>
                    Performances
                </h1>
                <div style="display: flex; gap: 10px;">
                    <a href="${pageContext.request.contextPath}/performances" class="btn-add" style="background: #1976d2;">
                        <i class="fas fa-sync-alt"></i>
                        Actualiser
                    </a>
                </div>
            </div>

            <p style="color: #6b7280; margin-bottom: 20px;">
                <i class="fas fa-info-circle"></i>
                Analyse des ventes et de la popularité des produits
            </p>

            <!-- Filtres de période -->
            <div class="filters-container">
                <form action="${pageContext.request.contextPath}/performances" method="get" class="filters-form">
                    
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

                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i>
                            Appliquer
                        </button>
                        <a href="${pageContext.request.contextPath}/performances" class="btn-filter-reset">
                            <i class="fas fa-undo"></i>
                            Réinitialiser
                        </a>
                    </div>
                    
                </form>
            </div>

            <!-- KPI Cards -->
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; margin: 20px 0;">
                
                <div style="background: #e3f2fd; padding: 15px 20px; border-radius: 8px; border-left: 4px solid #1976d2;">
                    <div style="display: flex; align-items: center; gap: 12px;">
                        <i class="fas fa-shopping-cart" style="font-size: 24px; color: #1976d2;"></i>
                        <div>
                            <div style="font-size: 12px; color: #6b7280;">Nombre de ventes</div>
                            <div style="font-size: 24px; font-weight: 700; color: #1976d2;">${stats.nombreVentes}</div>
                        </div>
                    </div>
                </div>

                <div style="background: #e8f5e9; padding: 15px 20px; border-radius: 8px; border-left: 4px solid #388e3c;">
                    <div style="display: flex; align-items: center; gap: 12px;">
                        <i class="fas fa-euro-sign" style="font-size: 24px; color: #388e3c;"></i>
                        <div>
                            <div style="font-size: 12px; color: #6b7280;">Chiffre d'affaires</div>
                            <div style="font-size: 24px; font-weight: 700; color: #388e3c;">
                                <fmt:formatNumber value="${stats.chiffreAffaires}" type="currency" currencySymbol="€" maxFractionDigits="2"/>
                            </div>
                        </div>
                    </div>
                </div>

                <div style="background: #fff3e0; padding: 15px 20px; border-radius: 8px; border-left: 4px solid #e65100;">
                    <div style="display: flex; align-items: center; gap: 12px;">
                        <i class="fas fa-cart-plus" style="font-size: 24px; color: #e65100;"></i>
                        <div>
                            <div style="font-size: 12px; color: #6b7280;">Panier moyen</div>
                            <div style="font-size: 24px; font-weight: 700; color: #e65100;">
                                <fmt:formatNumber value="${stats.moyennePanier}" type="currency" currencySymbol="€" maxFractionDigits="2"/>
                            </div>
                        </div>
                    </div>
                </div>

                <div style="background: #f3e5f5; padding: 15px 20px; border-radius: 8px; border-left: 4px solid #7b1fa2;">
                    <div style="display: flex; align-items: center; gap: 12px;">
                        <i class="fas fa-boxes" style="font-size: 24px; color: #7b1fa2;"></i>
                        <div>
                            <div style="font-size: 12px; color: #6b7280;">Produits vendus</div>
                            <div style="font-size: 24px; font-weight: 700; color: #7b1fa2;">${stats.nombreProduitsVendus}</div>
                        </div>
                    </div>
                </div>

            </div>

            <!-- Top 5 Produits -->
            <div style="margin-top: 30px;">
                <div class="table-header">
                    <h2>
                        <i class="fas fa-star" style="color: var(--primary); margin-right:10px;"></i>
                        Top 5 des produits les plus vendus
                    </h2>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th><i class="fas fa-hashtag"></i> #</th>
                            <th><i class="fas fa-box"></i> Produit</th>
                            <th><i class="fas fa-weight-hanging"></i> Quantité vendue</th>
                            <th><i class="fas fa-euro-sign"></i> Chiffre d'affaires</th>
                            <th><i class="fas fa-calculator"></i> Prix moyen</th>
                            <th><i class="fas fa-chart-simple"></i> Popularité</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty topProduits}">
                                <tr>
                                    <td colspan="6">
                                        <div class="empty-state">
                                            <i class="fas fa-chart-line" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                            <p>Aucune donnée de vente disponible</p>
                                            <p style="font-size: 14px; color: #6b7280;">
                                                <i class="fas fa-info-circle"></i>
                                                Commencez à enregistrer des commandes pour voir les performances
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${topProduits}" var="produit" varStatus="status">
                                    <tr>
                                        <td>
                                            <span class="badge ${status.index == 0 ? 'bg-warning text-dark' : status.index == 1 ? 'bg-secondary' : status.index == 2 ? 'bg-warning' : 'bg-light text-dark'}" 
                                                  style="font-size: 16px; font-weight: 700; padding: 6px 14px;">
                                                #${status.index + 1}
                                            </span>
                                        </td>
                                        <td>
                                            <strong>${produit.nomProduit}</strong>
                                        </td>
                                        <td>
                                            <span class="badge bg-info text-dark">
                                                <i class="fas fa-weight-hanging"></i>
                                                ${produit.quantiteVendue}
                                            </span>
                                        </td>
                                        <td>
                                            <span class="price-tag">
                                                <fmt:formatNumber value="${produit.chiffreAffaires}" type="currency" currencySymbol="€" maxFractionDigits="2"/>
                                            </span>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${produit.prixMoyen}" type="currency" currencySymbol="€" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <c:set var="max" value="${topProduits[0].quantiteVendue}"/>
                                            <c:set var="pourcentage" value="${max > 0 ? (produit.quantiteVendue / max * 100) : 0}"/>
                                            <c:set var="barColor" value="${status.index == 0 ? '#ffc107' : status.index == 1 ? '#6c757d' : status.index == 2 ? '#fd7e14' : '#17a2b8'}"/>
                                            
                                            <div style="display: flex; align-items: center; gap: 10px;">
                                                <div style="flex: 1; background: #e9ecef; border-radius: 10px; height: 8px; overflow: hidden;">
                                                    <div style="width: ${pourcentage}%; background: ${barColor}; height: 100%; border-radius: 10px;"></div>
                                                </div>
                                                <span style="font-size: 12px; color: #6b7280; min-width: 40px; text-align: right;">
                                                    ${Math.round(pourcentage)}%
                                                </span>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- Évolution des 7 derniers jours -->
            <div style="margin-top: 30px;">
                <div class="table-header">
                    <h2>
                        <i class="fas fa-calendar-alt" style="color: var(--primary); margin-right:10px;"></i>
                        Évolution des 7 derniers jours
                    </h2>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th><i class="fas fa-calendar-day"></i> Date</th>
                            <th><i class="fas fa-shopping-cart"></i> Nombre de commandes</th>
                            <th><i class="fas fa-euro-sign"></i> Chiffre d'affaires</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty last7Days}">
                                <tr>
                                    <td colspan="3">
                                        <div class="empty-state">
                                            <i class="fas fa-calendar-alt" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                            <p>Aucune donnée disponible pour les 7 derniers jours</p>
                                        </div>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${last7Days}" var="jour">
                                    <tr>
                                        <td>
                                            <strong>${jour[0]}</strong>
                                        </td>
                                        <td>
                                            <span class="badge bg-primary">
                                                <i class="fas fa-shopping-cart"></i>
                                                ${jour[1]}
                                            </span>
                                        </td>
                                        <td>
                                            <span class="price-tag">
                                                <fmt:formatNumber value="${jour[2]}" type="currency" currencySymbol="€" maxFractionDigits="2"/>
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- Filtres actifs -->
            <c:if test="${not empty dateDebut or not empty dateFin}">
                <div style="margin-top: 20px; display: flex; gap: 8px; flex-wrap: wrap;">
                    <span style="color: #6b7280; font-size: 13px; display: flex; align-items: center;">
                        <i class="fas fa-filter" style="margin-right: 5px;"></i>
                        Filtres actifs :
                    </span>
                    <c:if test="${not empty dateDebut}">
                        <span class="badge bg-primary">
                            <i class="fas fa-calendar-alt"></i>
                            Du : ${dateDebut}
                        </span>
                    </c:if>
                    <c:if test="${not empty dateFin}">
                        <span class="badge bg-success">
                            <i class="fas fa-calendar-alt"></i>
                            Au : ${dateFin}
                        </span>
                    </c:if>
                </div>
            </c:if>

        </div>

    </div>
</div>

</body>
</html>