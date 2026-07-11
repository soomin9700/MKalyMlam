<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des ventes</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="ventes"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp"/>

    <div class="main">

        <div class="table-container" style="margin-top:30px;">

            <div class="table-header">
                <h1>
                    <i class="fas fa-receipt" style="color: var(--primary); margin-right:10px;"></i>
                    Consultation des ventes
                </h1>
            </div>

            <!-- Filtres -->
            <form action="${pageContext.request.contextPath}/ventes" method="get" class="filter-form">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="dateDebut"><i class="fas fa-calendar"></i> Date debut</label>
                        <input type="date" name="dateDebut" id="dateDebut" value="${dateDebutSelectionne}">
                    </div>
                    <div class="filter-group">
                        <label for="dateFin"><i class="fas fa-calendar"></i> Date fin</label>
                        <input type="date" name="dateFin" id="dateFin" value="${dateFinSelectionne}">
                    </div>
                    <div class="filter-group">
                        <label for="idSession"><i class="fas fa-truck"></i> Session</label>
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
                        <label for="zone"><i class="fas fa-map-marker-alt"></i> Itineraire / Zone</label>
                        <select name="zone" id="zone">
                            <option value="">Toutes</option>
                            <c:forEach items="${itineraires}" var="it">
                                <option value="${it.nomZone}" ${zoneSelectionnee == it.nomZone ? 'selected' : ''}>
                                    ${it.nomZone}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filter-actions">
                        <button type="submit" class="btn-filter">
                            <i class="fas fa-filter"></i> Filtrer
                        </button>
                        <a href="${pageContext.request.contextPath}/ventes" class="btn-reset">
                            <i class="fas fa-undo"></i> Reinitialiser
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
                    <th><i class="fas fa-map-marker-alt"></i> Zone</th>
                    <th><i class="fas fa-tag"></i> Type</th>
                    <th><i class="fas fa-clock"></i> Recuperation</th>
                    <th><i class="fas fa-coins"></i> Montant</th>
                    <th><i class="fas fa-circle"></i> Statut</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty ventes}">
                    <tr>
                        <td colspan="8">
                            <div class="empty-state">
                                <i class="fas fa-receipt" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                <p>Aucune vente trouvee.</p>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <c:set var="totalVentes" value="0"/>
                <c:forEach items="${ventes}" var="v">
                    <c:set var="totalVentes" value="${totalVentes + v.montantTotal}"/>
                    <tr>
                        <td>
                            <span class="badge badge-id">#${v.idCommande}</span>
                        </td>
                        <td>
                            <fmt:parseDate value="${v.dateHeureCreation}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </td>
                        <td>
                            <c:if test="${v.sessionTruck != null}">
                                <span class="badge badge-id">#${v.sessionTruck.id}</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${v.sessionTruck != null}">
                                ${v.sessionTruck.itineraire.nomZone}
                            </c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${v.typeCommande.libelle == 'SUR_PLACE'}">
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#D1FAE5;color:#065F46;">SUR PLACE</span>
                                </c:when>
                                <c:when test="${v.typeCommande.libelle == 'A_DISTANCE'}">
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#FFEDD5;color:#9A3412;">A DISTANCE</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#F3F4F6;color:#374151;">${v.typeCommande.libelle}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${v.heureRecuperationPrevue != null}">
                                <fmt:parseDate value="${v.heureRecuperationPrevue}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedRecup" type="both"/>
                                <fmt:formatDate value="${parsedRecup}" pattern="dd/MM HH:mm"/>
                                <c:if test="${v.lieuRecuperationPrevu != null}">
                                    <br><small style="color:#6b7280;">${v.lieuRecuperationPrevu}</small>
                                </c:if>
                            </c:if>
                            <c:if test="${v.heureRecuperationPrevue == null}">
                                <span style="color:#9ca3af;">—</span>
                            </c:if>
                        </td>
                        <td>
                            <span style="font-weight:600;color:#059669;">
                                <fmt:formatNumber value="${v.montantTotal}" type="number" minFractionDigits="0"/> Ar
                            </span>
                        </td>
                        <td>
                            <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#D1FAE5;color:#065F46;">
                                LIVREE
                            </span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr style="background:#f9fafb;font-weight:700;">
                    <td colspan="6" style="text-align:right;">
                        <i class="fas fa-calculator"></i> Total :
                    </td>
                    <td colspan="2" style="color:#059669;font-size:16px;">
                        <fmt:formatNumber value="${totalVentes}" type="number" minFractionDigits="0"/> Ar
                        <small style="color:#6b7280;margin-left:10px;">(${ventes.size()} vente(s))</small>
                    </td>
                </tr>
                </tfoot>
            </table>

        </div>

    </div>
</div>

</body>
</html>
