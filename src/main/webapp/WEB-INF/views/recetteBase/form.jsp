<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>
        <c:choose>
            <c:when test="${isEdit}">
                Modifier une recette
            </c:when>
            <c:otherwise>
                Ajouter une recette
            </c:otherwise>
        </c:choose>
    </title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

<div class="dashboard">

    <c:set var="activeMenu" value="recettes"/>

    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <!-- Retour -->
        <a href="${pageContext.request.contextPath}/recetteBase"
           class="back-link">

            <i class="fas fa-arrow-left"></i>
            Retour à la liste des recettes

        </a>

        <div class="form-section">

            <form action="${actionUrl}" method="post">

                <!-- IDS cachés en mode EDIT -->
                <c:if test="${isEdit}">
                    <input type="hidden" name="ancienProduit" value="${recette.idProduit}">
                    <input type="hidden" name="ancienIngredient" value="${recette.idIngredient}">
                </c:if>

                <h1>

                    <i class="fas fa-utensils" style="color: var(--primary);"></i>

                    <c:choose>
                        <c:when test="${isEdit}">
                            Modifier une recette
                        </c:when>
                        <c:otherwise>
                            Ajouter une recette
                        </c:otherwise>
                    </c:choose>

                </h1>

                <p style="color:#6b7280;margin-bottom:30px;">

                    <c:choose>
                        <c:when test="${isEdit}">
                            Modifiez les informations de la recette sélectionnée.
                        </c:when>
                        <c:otherwise>
                            Remplissez tous les champs pour enregistrer une nouvelle recette.
                        </c:otherwise>
                    </c:choose>

                </p>

                <hr style="border:none;border-top:1px solid var(--gray);margin:20px 0;">
                <br>

                <!-- Produit -->
                <div class="form-group">

                    <label for="idProduit">
                        Produit *
                    </label>

                    <select name="idProduit" id="idProduit" required>

                        <option value="">-- Choisir un produit --</option>

                        <c:forEach items="${produits}" var="produit">

                            <option value="${produit.idProduit}"
                                ${produit.idProduit == recette.idProduit ? 'selected' : ''}>

                                ${produit.nomProduit}

                            </option>

                        </c:forEach>

                    </select>

                </div>

                <!-- Ingrédient -->
                <div class="form-group">

                    <label for="idIngredient">
                        Ingrédient *
                    </label>

                    <select name="idIngredient" id="idIngredient" required>

                        <option value="">-- Choisir un ingrédient --</option>

                        <c:forEach items="${ingredients}" var="ingredient">

                            <option value="${ingredient.idIngredient}"
                                ${ingredient.idIngredient == recette.idIngredient ? 'selected' : ''}>

                                ${ingredient.nomIngredient}

                            </option>

                        </c:forEach>

                    </select>

                </div>

                <!-- Quantité -->
                <div class="form-group">

                    <label for="quantiteRecette">
                        Quantité *
                    </label>

                    <input type="number"
                        id="quantiteRecette"
                        name="quantiteRecette"
                        step="0.001"
                        min="0"
                        value="${recette.quantiteRecette}"
                        placeholder="Ex: 1.5"
                        required
                    >

                </div>

                <!-- Boutons -->
                <div class="form-actions">

                    <button type="submit" class="btn-success">

                        <c:choose>
                            <c:when test="${isEdit}">
                                <i class="fas fa-save"></i>
                                Modifier
                            </c:when>
                            <c:otherwise>
                                <i class="fas fa-plus"></i>
                                Enregistrer
                            </c:otherwise>
                        </c:choose>

                    </button>

                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>

                    <a href="${pageContext.request.contextPath}/recetteBase"
                        style="margin-left:auto;
                        align-self:center;
                        color:var(--primary);
                        text-decoration:none;">

                        <i class="fas fa-times"></i>
                        Annuler

                    </a>

                </div>

            </form>

        </div>

    </div>

</div>

</body>
</html>