<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détail du stock</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="stocks"/>
    
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <a href="${pageContext.request.contextPath}/stocks/list" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste des stocks
        </a>

        <div class="detail-section">
            <h1>
                <i class="fas fa-box" style="color: var(--primary); margin-right: 12px;"></i>
                ${ingredient.nomIngredient}
            </h1>

            <div class="detail-grid">
                <div class="detail-card">
                    <span class="detail-label">Unité de mesure</span>
                    <span class="detail-value">${ingredient.uniteMesure}</span>
                </div>
                <div class="detail-card">
                    <span class="detail-label">Seuil d'alerte</span>
                    <span class="detail-value">${ingredient.seuilAlerte}</span>
                </div>
                <div class="detail-card">
                    <span class="detail-label">Quantité totale</span>
                    <span class="detail-value ${ingredient.quantiteTotale == 0 ? 'text-danger' : ''}">
                        ${ingredient.quantiteTotale}
                    </span>
                </div>
                <div class="detail-card">
                    <span class="detail-label">Valeur totale</span>
                    <span class="detail-value">
                        <fmt:formatNumber value="${ingredient.valeurTotale}" type="currency" currencySymbol="€" maxFractionDigits="2"/>
                    </span>
                </div>
                <div class="detail-card">
                    <span class="detail-label">Statut</span>
                    <span class="detail-value">
                        <c:choose>
                            <c:when test="${ingredient.statut == 'DISPONIBLE'}">
                                <span class="badge bg-success">Disponible</span>
                            </c:when>
                            <c:when test="${ingredient.statut == 'ALERTE'}">
                                <span class="badge bg-warning text-dark">Seuil d'alerte</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-danger">Rupture</span>
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>