<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Validation des depenses</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="validation"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <c:if test="${not empty success}">
            <div class="alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert-danger">${error}</div>
        </c:if>

        <div class="table-container">
            <div class="table-header">
                <h1>
                    <i class="fas fa-check-double" style="color:var(--primary);margin-right:10px;"></i>
                    Validation des depenses
                </h1>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Session</th>
                        <th>Type</th>
                        <th>Montant</th>
                        <th>Raison</th>
                        <th>Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty depenses}">
                        <tr>
                            <td colspan="7">
                                <div class="empty-state">
                                    <i class="fas fa-check-circle" style="font-size:48px;color:#22c55e;margin-bottom:15px;display:block;"></i>
                                    <p>Aucune depense en attente de validation.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach items="${depenses}" var="d">
                        <tr>
                            <td><span class="badge badge-id">${d.id}</span></td>
                            <td>#${d.session.id} - ${d.session.truck.immatriculation}</td>
                            <td><strong>${d.typeDepense.libelle}</strong></td>
                            <td><span style="font-weight:600;color:var(--primary);">${d.montantDepense} €</span></td>
                            <td>${d.raisonDetaillee}</td>
                            <td>${d.dateDepense}</td>
                            <td style="display:flex;gap:8px;">
                                <form method="post" action="${pageContext.request.contextPath}/admin/depenses/${d.id}/valider" style="display:inline;">
                                    <button class="btn-success" style="padding:8px 16px;"
                                            onclick="return confirm('Valider cette depense ?')">
                                        <i class="fas fa-check"></i> Valider
                                    </button>
                                </form>
                                <a href="${pageContext.request.contextPath}/admin/depenses/${d.id}/refuser"
                                   class="btn-danger" style="padding:8px 16px;text-decoration:none;display:inline-flex;align-items:center;gap:6px;">
                                    <i class="fas fa-times"></i> Refuser
                                </a>
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
