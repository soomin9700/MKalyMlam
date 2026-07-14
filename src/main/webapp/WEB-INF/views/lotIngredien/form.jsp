<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un lot d'ingrédient</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="lot-ingredients"/>
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <a href="${pageContext.request.contextPath}/lot/ingredients/new" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour
        </a>

        <div class="form-section">
            <form action="${actionUrl}" method="post">
                <h1>
                    <i class="fas fa-boxes" style="color: var(--primary); margin-right: 12px;"></i>
                    Ajouter un lot d'ingrédient
                </h1>

                <hr>
                <br>

                <div class="form-group">
                    <label for="ingredient">Ingrédient *</label>
                    <select id="ingredient" name="ingredient.idIngredient" required>
                        <option value="">Sélectionnez un ingrédient</option>
                        <c:forEach items="${ingredients}" var="ingredientItem">
                            <option value="${ingredientItem.idIngredient}">
                                ${ingredientItem.nomIngredient}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="dateReception">Date de réception *</label>
                    <input type="date" id="dateReception" name="dateReception" required>
                </div>

                <div class="form-group">
                    <label for="datePeremption">Date de péremption *</label>
                    <input type="date" id="datePeremption" name="datePeremption" required>
                </div>

                <div class="form-group">
                    <label for="quantiteInitiale">Quantité *</label>
                    <input type="number" id="quantiteInitiale" name="quantiteInitiale" step="0.01" min="0" required>
                </div>

                <div class="form-group">
                    <label for="prixAchatUnitaire">Prix unitaire *</label>
                    <input type="number" id="prixAchatUnitaire" name="prixAchatUnitaire" step="0.01" min="0" required>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-success">
                        <i class="fas fa-save"></i>
                        Enregistrer le lot
                    </button>
                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>
                </div>
            </form>
        </div>

        <div class="list-section">
            <h2>Liste des lots d'ingrédients</h2>

            <div class="filter-section">
                <form action="${pageContext.request.contextPath}/lot/ingredients/new" method="get">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="filterIngredient">Ingrédient</label>
                            <select id="filterIngredient" name="ingredientId">
                                <option value="">Tous les ingrédients</option>
                                <c:forEach items="${ingredients}" var="ingredientItem">
                                    <option value="${ingredientItem.idIngredient}"
                                            <c:if test="${ingredientItem.idIngredient == ingredientId}">selected</c:if>>
                                        ${ingredientItem.nomIngredient}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="dateMin">Date min</label>
                            <input type="date" id="dateMin" name="dateMin" value="${dateMin}" />
                        </div>
                        <div class="form-group">
                            <label for="dateMax">Date max</label>
                            <input type="date" id="dateMax" name="dateMax" value="${dateMax}" />
                        </div>
                        <div class="form-group filter-actions">
                            <button type="submit" class="btn-primary">Filtrer</button>
                            <a href="${pageContext.request.contextPath}/lot/ingredients/new" class="btn-secondary">Effacer</a>
                        </div>
                    </div>
                </form>
            </div>

            <c:if test="${empty lots}">
                <p>Aucun lot correspondant aux filtres.</p>
            </c:if>

            <c:if test="${not empty lots}">
                <table class="list-table">
                    <thead>
                    <tr>
                        <th>ID lot</th>
                        <th>Ingrédient</th>
                        <th>Date réception</th>
                        <th>Date péremption</th>
                        <th>Quantité</th>
                        <th>Prix unitaire</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${lots}" var="lot">
                        <tr>
                            <td>${lot.idLot}</td>
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
