<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ingrédients bientôt périmés</title>
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
                <h1>Ingrédients bientôt périmés</h1>
            </div>

            <!-- <form method="get" action="${pageContext.request.contextPath}/lot/ingredients/view/bientot-perimes" style="margin-bottom: 20px;">
                <div style="display:flex; gap:10px; flex-wrap:wrap; align-items:end;">
                    <div>
                        <label for="ingredientId">Ingrédient</label><br/>
                        <select name="ingredientId" id="ingredientId">
                            <option value="">Tous</option>
                            <c:forEach var="ingredient" items="${ingredients}">
                                <option value="${ingredient.idIngredient}" ${ingredient.idIngredient == selectedIngredientId ? 'selected' : ''}>
                                    ${ingredient.nomIngredient}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label for="dateMin">Date min</label><br/>
                        <input type="date" name="dateMin" id="dateMin" value="${selectedDateMin}" />
                    </div>
                    <div>
                        <label for="dateMax">Date max</label><br/>
                        <input type="date" name="dateMax" id="dateMax" value="${selectedDateMax}" />
                    </div>
                    <div>
                        <button type="submit" class="btn-edit">Filtrer</button>
                    </div>
                </div>
            </form> -->

            <c:if test="${empty lots}">
                <p>Aucun lot bientôt périmé.</p>
            </c:if>

            <c:if test="${not empty lots}">
                <table>
                    <thead>
                    <tr>
                        <th>Ingrédient</th>
                        <th>Date de réception</th>
                        <th>Date de péremption</th>
                        <th>Quantité restante</th>
                        <th>Prix unitaire</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="lot" items="${lots}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/lot/ingredients/view/bientot-perimes/${lot.ingredient.idIngredient}">
                                    ${lot.ingredient.nomIngredient}
                                </a>
                            </td>
                            <td>${lot.dateReception}</td>
                            <td>${lot.datePeremption}</td>
                            <td>${quantitesRestantes[lot.idLot] != null ? quantitesRestantes[lot.idLot] : lot.quantiteInitiale}</td>
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
