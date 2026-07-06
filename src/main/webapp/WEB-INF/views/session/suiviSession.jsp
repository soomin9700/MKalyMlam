<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Suivi de la session #${session.id} - Administration</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="sessionTruck"/>

    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <a href="${pageContext.request.contextPath}/session/liste" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste
        </a>

        <!-- En-tête -->
        <div style="display:flex;align-items:center;gap:12px;margin-bottom:25px;flex-wrap:wrap;">
            <h1 style="margin:0;">
                <i class="fas fa-chart-line" style="color: var(--primary); margin-right:10px;"></i>
                Suivi de la session #${session.id}
            </h1>
            <span style="display:inline-block;padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;
                <c:choose>
                    <c:when test="${session.statutSession.libelle == 'OUVERTE'}">background:#D1FAE5;color:#065F46;</c:when>
                    <c:otherwise>background:#FEE2E2;color:#991B1B;</c:otherwise>
                </c:choose>">
                ${session.statutSession.libelle}
            </span>
        </div>

        <!-- Informations générales -->
        <div class="table-container" style="margin-bottom:25px;">
            <div class="table-header">
                <h2 style="font-size:18px;margin:0;">
                    <i class="fas fa-info-circle" style="color:var(--primary);margin-right:8px;"></i>
                    Informations générales
                </h2>
            </div>
            <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:20px;padding:20px;">
                <div>
                    <div style="font-size:13px;color:#6b7280;">Camion</div>
                    <div style="font-weight:600;font-size:16px;">${session.truck.immatriculation}</div>
                </div>
                <div>
                    <div style="font-size:13px;color:#6b7280;">Itinéraire</div>
                    <div style="font-weight:600;font-size:16px;">${session.itineraire.nomZone}</div>
                </div>
                <div>
                    <div style="font-size:13px;color:#6b7280;">Date</div>
                    <div style="font-weight:600;font-size:16px;">${session.dateSession}</div>
                </div>
                <div>
                    <div style="font-size:13px;color:#6b7280;">Fond de caisse départ</div>
                    <div style="font-weight:600;font-size:16px;">€ <fmt:formatNumber value="${session.fondDeCaisseOuverture}" minFractionDigits="2"/></div>
                </div>
                <c:if test="${session.fondDeCaisseCloture != null}">
                    <div>
                        <div style="font-size:13px;color:#6b7280;">Fond de caisse clôture</div>
                        <div style="font-weight:600;font-size:16px;">€ <fmt:formatNumber value="${session.fondDeCaisseCloture}" minFractionDigits="2"/></div>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Indicateurs clés -->
        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:20px;margin-bottom:25px;">
            <div style="background:linear-gradient(135deg,#16A34A,#15803D);color:#fff;border-radius:14px;padding:22px;">
                <div style="font-size:14px;opacity:.9;"><i class="fas fa-coins"></i> Chiffre d'affaires</div>
                <div style="font-size:28px;font-weight:700;margin-top:8px;">€ <fmt:formatNumber value="${chiffreAffaire}" minFractionDigits="2"/></div>
            </div>
            <div style="background:linear-gradient(135deg,#2563EB,#1D4ED8);color:#fff;border-radius:14px;padding:22px;">
                <div style="font-size:14px;opacity:.9;"><i class="fas fa-shopping-cart"></i> Ventes réalisées</div>
                <div style="font-size:28px;font-weight:700;margin-top:8px;">${nombreVentes}</div>
            </div>
            <div style="background:linear-gradient(135deg,#DC2626,#B91C1C);color:#fff;border-radius:14px;padding:22px;">
                <div style="font-size:14px;opacity:.9;"><i class="fas fa-receipt"></i> Dépenses</div>
                <div style="font-size:28px;font-weight:700;margin-top:8px;">€ <fmt:formatNumber value="${totalDepenses}" minFractionDigits="2"/></div>
            </div>
            <div style="background:linear-gradient(135deg,#7C3AED,#6D28D9);color:#fff;border-radius:14px;padding:22px;">
                <div style="font-size:14px;opacity:.9;"><i class="fas fa-wallet"></i> Résultat (CA - dépenses)</div>
                <div style="font-size:28px;font-weight:700;margin-top:8px;">
                    € <fmt:formatNumber value="${chiffreAffaire - totalDepenses}" minFractionDigits="2"/>
                </div>
            </div>
        </div>

        <!-- Ventes / Commandes -->
        <div class="table-container" style="margin-bottom:25px;">
            <div class="table-header">
                <h2 style="font-size:18px;margin:0;">
                    <i class="fas fa-shopping-bag" style="color:var(--primary);margin-right:8px;"></i>
                    Commandes (${nombreVentes})
                </h2>
            </div>

            <table>
                <thead>
                    <tr>
                        <th><i class="fas fa-hashtag"></i> N°</th>
                        <th><i class="fas fa-clock"></i> Date/Heure</th>
                        <th><i class="fas fa-user"></i> Vendeuse</th>
                        <th><i class="fas fa-tag"></i> Type</th>
                        <th><i class="fas fa-circle"></i> Statut</th>
                        <th><i class="fas fa-coins"></i> Montant</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty commandes}">
                        <tr>
                            <td colspan="6">
                                <div class="empty-state">
                                    <i class="fas fa-shopping-bag" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                    <p>Aucune commande pour cette session.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach var="cmd" items="${commandes}">
                        <tr>
                            <td><span class="badge badge-id">${cmd.idCommande}</span></td>
                            <td>${cmd.dateHeureCreation}</td>
                            <td>${cmd.vendeuse}</td>
                            <td>${cmd.typeCommande}</td>
                            <td>${cmd.statutCommande}</td>
                            <td><strong style="color:var(--primary);">€ <fmt:formatNumber value="${cmd.montantTotal}" minFractionDigits="2"/></strong></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Dépenses -->
        <div class="table-container">
            <div class="table-header">
                <h2 style="font-size:18px;margin:0;">
                    <i class="fas fa-receipt" style="color:var(--primary);margin-right:8px;"></i>
                    Dépenses
                </h2>
            </div>

            <table>
                <thead>
                    <tr>
                        <th><i class="fas fa-hashtag"></i> N°</th>
                        <th><i class="fas fa-tags"></i> Type</th>
                        <th><i class="fas fa-align-left"></i> Raison</th>
                        <th><i class="fas fa-clock"></i> Date</th>
                        <th><i class="fas fa-circle"></i> Statut</th>
                        <th><i class="fas fa-coins"></i> Montant</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty depenses}">
                        <tr>
                            <td colspan="6">
                                <div class="empty-state">
                                    <i class="fas fa-receipt" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                    <p>Aucune dépense pour cette session.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach var="dep" items="${depenses}">
                        <tr>
                            <td><span class="badge badge-id">${dep.idDepense}</span></td>
                            <td>${dep.typeDepense}</td>
                            <td>${dep.raisonDetaillee}</td>
                            <td>${dep.dateDepense}</td>
                            <td>${dep.statutValidation}</td>
                            <td><strong style="color:#DC2626;">€ <fmt:formatNumber value="${dep.montantDepense}" minFractionDigits="2"/></strong></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>

</body>
</html>
