<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lots bientôt périmés</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="ingredients"/>
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <div class="table-container">
            <div class="table-header">
                <h1>Lots bientôt périmés - ${ingredient.nomIngredient}</h1>
                <a href="${pageContext.request.contextPath}/lot/ingredients/view/bientot-perimes" class="btn-edit">
                    Retour à la liste générale
                </a>
            </div>

            <c:if test="${empty lots}">
                <p>Aucun lot bientôt périmé pour cet ingrédient.</p>
            </c:if>

            <c:if test="${not empty lots}">
                <table>
                    <thead>
                    <tr>
                        <th>Ingrédient</th>
                        <th>Date de réception</th>
                        <th>Date de péremption</th>
                        <th>Quantité</th>
                        <th>Prix unitaire</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="lot" items="${lots}">
                        <tr>
                            <td>${lot.ingredient.nomIngredient}</td>
                            <td>${lot.dateReception}</td>
                            <td>${lot.datePeremption}</td>
                            <td>${lot.quantiteInitiale}</td>
                            <td>${lot.prixAchatUnitaire}</td>
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
