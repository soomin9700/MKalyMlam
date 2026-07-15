<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>État du stock</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="mouvements-equipement"/>
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <a href="${pageContext.request.contextPath}/mouvements-equipement" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour au mouvement des équipements
        </a>

        <div class="table-container">
            <div class="table-header">
                <h1>État du stock</h1>
                <a href="${pageContext.request.contextPath}/equipements/export" class="btn-secondary" style="margin-left: 16px; padding: 8px 14px; display: inline-block; text-decoration: none;">
                    <i class="fas fa-file-csv"></i>&nbsp;Exporter CSV
                </a>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Équipement</th>
                    <th>Catégorie</th>
                    <th>Quantité actuelle</th>
                    <th>Quantité minimale</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${equipements}" var="equipement">
                    <tr>
                        <td>${equipement.nomEquipement}</td>
                        <td>${equipement.typeEquipement.libelle}</td>
                        <td>${stockMap[equipement.idEquipement]}</td>
                        <td>${equipement.quantiteMin}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
