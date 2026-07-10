<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des commandes</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="commande"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp"/>

    <div class="main">

        <c:if test="${not empty success}">
            <div style="background: #D1FAE5; color: #065F46; padding: 15px 20px; border-radius: 10px; margin-bottom: 20px; display: flex; align-items: center; gap: 10px; border-left: 4px solid #16A34A;">
                <i class="fas fa-check-circle" style="font-size: 20px;"></i>
                <span>${success}</span>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div style="background: #FEE2E2; color: #991B1B; padding: 15px 20px; border-radius: 10px; margin-bottom: 20px; display: flex; align-items: center; gap: 10px; border-left: 4px solid #EF4444;">
                <i class="fas fa-exclamation-circle" style="font-size: 20px;"></i>
                <span>${error}</span>
            </div>
        </c:if>

        <div class="table-container" style="margin-top:30px;">

            <div class="table-header">
                <h1>
                    <i class="fas fa-shopping-cart" style="color: var(--primary); margin-right:10px;"></i>
                    Commandes
                </h1>
            </div>

            <!-- Filtres -->
            <form action="${pageContext.request.contextPath}/commande/liste" method="get" class="filter-form">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="statut">Statut</label>
                        <select name="statut" id="statut">
                            <option value="">Toutes</option>
                            <c:forEach items="${statuts}" var="s">
                                <option value="${s.libelle}" ${selectedStatut == s.libelle ? 'selected' : ''}>
                                    ${s.libelle}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="type">Type</label>
                        <select name="type" id="type">
                            <option value="">Tous</option>
                            <c:forEach items="${types}" var="t">
                                <option value="${t.libelle}" ${selectedType == t.libelle ? 'selected' : ''}>
                                    ${t.libelle}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i> Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/commande/liste" class="btn-reset">
                            <i class="fas fa-undo"></i> Réinitialiser
                        </a>
                    </div>
                </div>
            </form>

            <!-- Tableau -->
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th><i class="fas fa-calendar"></i> Date</th>
                    <th><i class="fas fa-truck"></i> Session</th>
                    <th><i class="fas fa-tag"></i> Type</th>
                    <th><i class="fas fa-coins"></i> Montant</th>
                    <th><i class="fas fa-circle"></i> Statut</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty commandes}">
                    <tr>
                        <td colspan="6">
                            <div class="empty-state">
                                <i class="fas fa-shopping-cart" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                <p>Aucune commande trouvée.</p>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <c:forEach items="${commandes}" var="cmd">
                    <tr>
                        <td>
                            <span class="badge badge-id">#${cmd.idCommande}</span>
                        </td>
                        <td>
                            <fmt:parseDate value="${cmd.dateHeureCreation}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </td>
                        <td>
                            <c:if test="${cmd.sessionTruck != null}">
                                <span class="badge badge-id">#${cmd.sessionTruck.id}</span>
                                <br>
                                <small style="color:#6b7280;">
                                    ${cmd.sessionTruck.itineraire.nomZone} — ${cmd.sessionTruck.dateSession}
                                </small>
                            </c:if>
                            <c:if test="${cmd.sessionTruck == null}">
                                <span style="color:#9ca3af;">—</span>
                            </c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${cmd.typeCommande.libelle == 'SUR_PLACE'}">
                                    <span style="display:inline-block;padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#D1FAE5;color:#065F46;">
                                        SUR PLACE
                                    </span>
                                </c:when>
                                <c:when test="${cmd.typeCommande.libelle == 'A_DISTANCE'}">
                                    <span style="display:inline-block;padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#FFEDD5;color:#9A3412;">
                                        À DISTANCE
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span style="display:inline-block;padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#F3F4F6;color:#374151;">
                                        ${cmd.typeCommande.libelle}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <span style="font-weight:600;color:var(--primary);">
                                <fmt:formatNumber value="${cmd.montantTotal}" type="number" minFractionDigits="2"/> €
                            </span>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/commande/changerStatut" method="post"
                                  style="display:inline;">
                                <input type="hidden" name="idCommande" value="${cmd.idCommande}">
                                <select name="statut" onchange="this.form.submit()"
                                        style="padding:5px 10px;border:1px solid var(--gray);border-radius:8px;font-size:12px;font-weight:600;cursor:pointer;
                                        <c:choose>
                                            <c:when test="${cmd.statutCommande.libelle == 'EN_ATTENTE'}">background:#FEF3C7;color:#92400E;</c:when>
                                            <c:when test="${cmd.statutCommande.libelle == 'PREPARATION'}">background:#DBEAFE;color:#1E40AF;</c:when>
                                            <c:when test="${cmd.statutCommande.libelle == 'PRETE_POUR_RECUPERATION'}">background:#EDE9FE;color:#5B21B6;</c:when>
                                            <c:when test="${cmd.statutCommande.libelle == 'LIVREE'}">background:#D1FAE5;color:#065F46;</c:when>
                                            <c:when test="${cmd.statutCommande.libelle == 'ANNULEE'}">background:#FEE2E2;color:#991B1B;</c:when>
                                        </c:choose>">
                                    <c:forEach items="${statuts}" var="s">
                                        <option value="${s.libelle}" ${cmd.statutCommande.libelle == s.libelle ? 'selected' : ''}>
                                            ${s.libelle}
                                        </option>
                                    </c:forEach>
                                </select>
                            </form>
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
