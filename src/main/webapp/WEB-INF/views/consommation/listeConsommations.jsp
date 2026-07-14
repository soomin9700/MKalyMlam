<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Historique des consommations</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="consommation"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp"/>

    <div class="main">

        <div class="table-container" style="margin-top:30px;">

            <div class="table-header">
                <h1>
                    <i class="fas fa-flask" style="color: var(--primary); margin-right:10px;"></i>
                    Historique des consommations
                </h1>
            </div>

            <form action="${pageContext.request.contextPath}/consommation/historique" method="get" class="filter-form">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="idSession">Session</label>
                        <select name="idSession" id="idSession">
                            <option value="">Toutes</option>
                            <c:forEach items="${sessions}" var="s">
                                <option value="${s.idSession}" ${idSessionSelectionne == s.idSession ? 'selected' : ''}>
                                    #${s.idSession} — ${s.itineraire.nomZone} (${s.dateSession})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="idIngredient">Ingrédient</label>
                        <select name="idIngredient" id="idIngredient">
                            <option value="">Tous</option>
                            <c:forEach items="${ingredients}" var="ing">
                                <option value="${ing.idIngredient}" ${idIngredientSelectionne == ing.idIngredient ? 'selected' : ''}>
                                    ${ing.nomIngredient} (${ing.uniteMesure})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i> Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/consommation/historique" class="btn-reset">
                            <i class="fas fa-undo"></i> Réinitialiser
                        </a>
                    </div>
                </div>
            </form>

            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th><i class="fas fa-shopping-cart"></i> Commande</th>
                    <th><i class="fas fa-carrot"></i> Ingrédient</th>
                    <th><i class="fas fa-weight-hanging"></i> Quantité</th>
                    <th><i class="fas fa-truck"></i> Session</th>
                    <th><i class="fas fa-clock"></i> Date</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty historiques}">
                    <tr>
                        <td colspan="6">
                            <div class="empty-state">
                                <i class="fas fa-flask" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                <p>Aucune consommation enregistrée.</p>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <c:forEach items="${historiques}" var="h">
                    <tr>
                        <td>
                            <span class="badge badge-id">#${h.idConsommation}</span>
                        </td>
                        <td>
                            <c:if test="${h.commande != null}">
                                <span class="badge badge-id">#${h.commande.idCommande}</span>
                            </c:if>
                            <c:if test="${h.commande == null}">
                                <span style="color:#9ca3af;">—</span>
                            </c:if>
                        </td>
                        <td>
                            <span style="font-weight:600;">${h.ingredient.nomIngredient}</span>
                            <br>
                            <small style="color:#6b7280;">${h.ingredient.uniteMesure}</small>
                        </td>
                        <td>
                            <span style="font-weight:600;color:#DC2626;">
                                -<fmt:formatNumber value="${h.quantiteConsommee}" type="number" minFractionDigits="2"/>
                            </span>
                        </td>
                        <td>
                            <c:if test="${h.session != null}">
                                <span class="badge badge-id">#${h.session.idSession}</span>
                                <br>
                                <small style="color:#6b7280;">
                                    ${h.session.itineraire.nomZone}
                                </small>
                            </c:if>
                        </td>
                        <td>
                            <fmt:parseDate value="${h.dateConsommation}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm"/>
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
