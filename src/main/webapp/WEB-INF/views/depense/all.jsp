<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Toutes les depenses</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="depenses"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <div class="table-header">
                <h1>
                    <i class="fas fa-money-bill-wave" style="color:var(--primary);margin-right:10px;"></i>
                    Toutes les depenses
                </h1>
            </div>

            <form method="get" action="${pageContext.request.contextPath}/depenses"
                  style="display:grid;grid-template-columns:1fr 1fr 1fr auto;gap:14px;align-items:end;margin-bottom:20px;padding:16px 18px;background:#f9fafb;border:1px solid #e5e7eb;border-radius:14px;">

                <div class="form-group" style="margin-bottom:0;">
                    <label for="idSession">Session</label>
                    <select id="idSession" name="idSession" style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;width:100%;">
                        <option value="">Toutes les sessions</option>
                        <c:forEach items="${sessions}" var="s">
                            <option value="${s.id}" ${selectedSession == s.id ? 'selected' : ''}>
                                #${s.id} - ${s.truck.immatriculation} - ${s.dateSession}
                            </option>
                        </c:forEach>
                    </select>
                </div>

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
                        <th><i class="fas fa-truck"></i> Session</th>
                        <th><i class="fas fa-tag"></i> Type</th>
                        <th><i class="fas fa-euro-sign"></i> Montant</th>
                        <th><i class="fas fa-align-left"></i> Raison</th>
                        <th><i class="fas fa-calendar"></i> Date</th>
                        <th><i class="fas fa-check-circle"></i> Statut</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty depenses}">
                        <tr>
                            <td colspan="7">
                                <div class="empty-state">
                                    <i class="fas fa-money-bill-wave" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                    <p>Aucune depense enregistree.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach items="${depenses}" var="d">
                        <tr>
                            <td><span class="badge badge-id">${d.id}</span></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/session/${d.session.id}/depenses"
                                   style="color:var(--primary);text-decoration:none;font-weight:600;">
                                    #${d.session.id} - ${d.session.truck.immatriculation}
                                </a>
                            </td>
                            <td><strong>${d.typeDepense.libelle}</strong></td>
                            <td><span style="font-weight:600;color:var(--primary);">${d.montantDepense} €</span></td>
                            <td>${d.raisonDetaillee}</td>
                            <td>${d.dateDepense}</td>
                            <td>
                                <span class="badge bg-warning text-dark">
                                    ${d.statutValidationAdmin.libelle}
                                </span>
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
