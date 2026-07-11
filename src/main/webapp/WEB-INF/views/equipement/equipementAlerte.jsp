<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Alertes d'équipements</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="dashboard">
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <a href="${pageContext.request.contextPath}/equipements" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour aux équipements
        </a>

        <div class="table-container">
            <div class="table-header">
                <h1>Alertes d'équipements</h1>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Catégorie</th>
                    <th>Méthode comptable</th>
                    <th>Prix unitaire</th>
                    <th>Quantité minimale</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${equipements}" var="equipement">
                    <tr>
                        <td>${equipement.nomEquipement}</td>
                        <td>${equipement.typeEquipement.libelle}</td>
                        <td>${equipement.methodeComptable.libelle}</td>
                        <td>${equipement.prixUnitaire}</td>
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
