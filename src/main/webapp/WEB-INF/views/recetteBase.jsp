<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recette de Base</title>
</head>
<body>


    <h2>Lier un produit à un ingrédient</h2>
    <form action="/recetteBase/save" method="post">
        <label>Produit :
            <select name="id_produit">
                <option value="1">Sandwich poulet</option>
                <option value="2">Burger boeuf</option>
                <option value="3">Hot dog</option>
                <option value="4">Salade cesar</option>
                <option value="5">Pizza fromage</option>
            </select>
        </label>
        <br/>
        <label>Ingrédient :
            <select name="id_ingredient">
                <option value="1">Pain burger</option>
                <option value="2">Blanc de poulet</option>
                <option value="3">Steak boeuf</option>
                <option value="4">Salade verte</option>
                <option value="5">Tomate</option>
                <option value="6">Fromage râpé</option>
                <option value="7">Saucisse</option>
                <option value="8">Pâte à pizza</option>
                <option value="9">Sauce tomate</option>
            </select>
        </label>
        <br/>
        <label>Quantité :
            <input type="number" step="0.001" name="quantite_recette" required/>
        </label>
        <br/><br/>
        <button type="submit">Lier</button>
    </form>

    <hr/>
    <h2>Liste des recettes</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>ID Produit</th>
                <th>ID Ingrédient</th>
                <th>Quantité</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${recettes}" var="recette">
                <tr>
                    <td>${recette.id}</td>
                    <td>${recette.id_produit}</td>
                    <td>${recette.id_ingredient}</td>
                    <td>${recette.quantite_recette}</td>
                    <td><a href="/recetteBase/delete?id=${recette.id}" onclick="return confirm('Supprimer ?')">Supprimer</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
