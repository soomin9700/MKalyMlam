<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestion des recettes de base</title>
</head>
<body>

<h1>Gestion des recettes de base</h1>
<hr>

<h2>Ajouter une recette</h2>

<form action="${pageContext.request.contextPath}/recetteBase/save" method="post">

    <label>Produit :</label>
    <select name="idProduit" required>
        <option value="">-- Choisir un produit --</option>
        <c:forEach items="${produits}" var="produit">
            <option value="${produit.id}">${produit.nomProduit}</option>
        </c:forEach>
    </select>

    <br><br>

    <label>Ingrédient :</label>
    <select name="idIngredient" required>
        <option value="">-- Choisir un ingrédient --</option>
        <c:forEach items="${ingredients}" var="ingredient">
            <option value="${ingredient.id}">${ingredient.nomIngredient}</option>
        </c:forEach>
    </select>

    <br><br>

    <label>Quantité :</label>
    <input type="number" name="quantiteRecette" step="0.001" min="0" required>

    <br><br>

    <button type="submit">Enregistrer</button>
</form>

<hr>

<h2>Liste des recettes</h2>

<table border="1" cellpadding="8">
    <thead>
        <tr>
            <th>Produit</th>
            <th>Ingrédient</th>
            <th>Quantité</th>
            <th>Action</th>
        </tr>
    </thead>

    <tbody>
        <c:choose>
            <c:when test="${not empty recettes}">
                <c:forEach items="${recettes}" var="recette">
                    <tr>

                       
                        <td>
                            <c:forEach items="${produits}" var="produit">
                                <c:if test="${produit.id == recette.idProduit}">
                                    ${produit.nomProduit}
                                </c:if>
                            </c:forEach>
                        </td>

                        <td>
                            <c:forEach items="${ingredients}" var="ingredient">
                                <c:if test="${ingredient.id == recette.idIngredient}">
                                    ${ingredient.nomIngredient}
                                </c:if>
                            </c:forEach>
                        </td>

                      
                        <td>${recette.quantiteRecette}</td>

                      
                        <td>
                            <a href="${pageContext.request.contextPath}/recetteBase/delete?idProduit=${recette.idProduit}&idIngredient=${recette.idIngredient}"
                               onclick="return confirm('Supprimer cette recette ?')">
                                Supprimer
                            </a>
                        </td>

                    </tr>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <tr>
                    <td colspan="4" align="center">Aucune recette enregistrée.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>

</table>

</body>
</html>