<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Depenses - Session #${session.id}</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="sessions"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <c:if test="${not empty success}">
            <div style="background:#D1FAE5;color:#065F46;padding:15px 20px;border-radius:10px;margin-bottom:20px;display:flex;align-items:center;gap:10px;border-left:4px solid #16A34A;">
                <i class="fas fa-check-circle" style="font-size:20px;"></i>
                <span>${success}</span>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div style="background:#FEE2E2;color:#991B1B;padding:15px 20px;border-radius:10px;margin-bottom:20px;display:flex;align-items:center;gap:10px;border-left:4px solid #EF4444;">
                <i class="fas fa-exclamation-circle" style="font-size:20px;"></i>
                <span>${error}</span>
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/session/liste" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour aux sessions
        </a>

        <div class="table-container">

            <div class="table-header">
                <h1>
                    <i class="fas fa-money-bill-wave" style="color:var(--primary);margin-right:10px;"></i>
                    Depenses - Session #${session.id}
                </h1>
                <div style="display:flex;gap:10px;">
                    <span style="padding:6px 14px;background:#f3f4f6;border-radius:8px;font-size:14px;">
                        <strong>${session.truck.immatriculation}</strong>
                    </span>
                    <span style="padding:6px 14px;background:#f3f4f6;border-radius:8px;font-size:14px;">
                        ${session.dateSession}
                    </span>
                    <c:if test="${session.statutSession.libelle == 'OUVERTE'}">
                        <a href="${pageContext.request.contextPath}/session/${session.id}/depenses/ajouter" class="btn-add">
                            <i class="fas fa-plus"></i>
                            Ajouter une depense
                        </a>
                    </c:if>
                </div>
            </div>

            <form method="get" action="${pageContext.request.contextPath}/session/${session.id}/depenses"
                  style="display:grid;grid-template-columns:1fr 1fr auto;gap:14px;align-items:end;margin-bottom:20px;padding:16px 18px;background:#f9fafb;border:1px solid #e5e7eb;border-radius:14px;">

                <div class="form-group" style="margin-bottom:0;">
                    <label for="idTypeDepense">Type de depense</label>
                    <select id="idTypeDepense" name="idTypeDepense" style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;width:100%;">
                        <option value="">Tous les types</option>
                        <c:forEach items="${types}" var="t">
                            <option value="${t.id}" ${selectedType == t.id ? 'selected' : ''}>${t.libelle}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group" style="margin-bottom:0;">
                    <label for="idStatut">Statut</label>
                    <select id="idStatut" name="idStatut" style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;width:100%;">
                        <option value="">Tous les statuts</option>
                        <c:forEach items="${statuts}" var="s">
                            <option value="${s.id}" ${selectedStatut == s.id ? 'selected' : ''}>${s.libelle}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn-success" style="height:44px;">
                    <i class="fas fa-filter"></i>
                    Filtrer
                </button>
            </form>

            <table>
                <thead>
                    <tr>
                        <th><i class="fas fa-hashtag"></i> ID</th>
                        <th><i class="fas fa-tag"></i> Type</th>
                        <th><i class="fas fa-euro-sign"></i> Montant</th>
                        <th><i class="fas fa-align-left"></i> Raison</th>
                        <th><i class="fas fa-calendar"></i> Date</th>
                        <th><i class="fas fa-check-circle"></i> Statut</th>
                        <th><i class="fas fa-cog"></i> Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty depenses}">
                        <tr>
                            <td colspan="7">
                                <div class="empty-state">
                                    <i class="fas fa-money-bill-wave" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                    <p>Aucune depense pour cette session.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach items="${depenses}" var="d">
                        <tr>
                            <td><span class="badge badge-id">${d.id}</span></td>
                            <td><strong>${d.typeDepense.libelle}</strong></td>
                            <td><span style="font-weight:600;color:var(--primary);">${d.montantDepense} €</span></td>
                            <td>${d.raisonDetaillee}</td>
                            <td>${d.dateDepense}</td>
                            <td>
                                <span class="badge bg-warning text-dark">
                                    ${d.statutValidationAdmin.libelle}
                                </span>
                            </td>
                            <td>
                                <div class="actions">
                                    <c:if test="${d.statutValidationAdmin.libelle == 'EN_ATTENTE'}">
                                        <a href="${pageContext.request.contextPath}/session/${session.id}/depenses/${d.id}/modifier"
                                           class="btn-edit">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <form action="${pageContext.request.contextPath}/session/${session.id}/depenses/${d.id}/supprimer"
                                              method="post"
                                              style="display:inline;"
                                              onsubmit="return confirm('Supprimer cette depense ?');">
                                            <button type="submit" class="btn-delete">
                                                <i class="fas fa-trash-alt"></i>
                                            </button>
                                        </form>
                                    </c:if>
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
