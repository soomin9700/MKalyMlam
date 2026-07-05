<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détail approvisionnement</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="approvisionnements"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <a href="${pageContext.request.contextPath}/approvisionnements" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour aux approvisionnements
        </a>

        <div class="table-container">
            <div class="table-header">
                <h1>
                    <i class="fas fa-file-invoice" style="color:var(--primary);margin-right:10px;"></i>
                    Approvisionnement N° ${approvisionnement.idApprovisionnement}
                </h1>

                <div style="font-weight:600;color:#374151;">
                    ${approvisionnement.dateApprovisionnement}
                    -
                    Total : ${approvisionnement.coutTotalEstime}
                </div>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Ingrédient</th>
                    <th>Stock actuel</th>
                    <th>Prix estimé unitaire</th>
                    <th>Quantité à acheter</th>
                    <th>Coût estimé</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${details}" var="detail">
                    <tr>
                        <td><strong>${detail.ingredient.nomIngredient}</strong></td>
                        <td>${detail.stockActuel}</td>
                        <td>${detail.prixEstimeUnitaire}</td>
                        <td>${detail.quantiteAAcheter}</td>
                        <td><span class="price-tag">${detail.coutEstime}</span></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>

</body>
</html>
