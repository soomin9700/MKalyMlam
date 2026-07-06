<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un lot d'ingrédient</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
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

                <div class="form-group">
                    <label for="typeMouvement">Type de mouvement *</label>
                    <select id="typeMouvement" name="typeMouvement.idTypeMouvement" required>
                        <option value="">Sélectionnez un type</option>
                        <c:forEach items="${typeMouvements}" var="typeMouvementItem">
                            <option value="${typeMouvementItem.idTypeMouvement}">
                                ${typeMouvementItem.libelle}
                            </option>
                        </c:forEach>
                    </select>
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
    </div>
</div>
</body>
</html>
