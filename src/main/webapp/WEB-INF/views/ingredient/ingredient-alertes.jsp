<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Alertes ingrédients</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="lot-ingredients-alertes"/>
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <div class="table-container">
            <div class="table-header">
                <h1>Alerte ingrédients</h1>
            </div>

            <c:if test="${empty ingredientsAlerte}">
                <p>Aucun ingrédient en alerte pour le moment.</p>
            </c:if>

            <c:if test="${not empty ingredientsAlerte}">
                <table>
                    <thead>
                    <tr>
                        <th>Ingrédient</th>
                        <th>Quantité totale</th>
                        <th>Seuil d'alerte</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ingredientsAlerte}" var="ingredient">
                        <tr>
                            <td>${ingredient.nomIngredient}</td>
                            <td>${quantitesParIngredient[ingredient.idIngredient]}</td>
                            <td>${ingredient.seuilAlerteQuantite}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
