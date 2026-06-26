<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestion des itinéraires</title>
</head>
<body>

<h1>Gestion des itinéraires</h1>
<hr>

<h2>Ajouter un itinéraire</h2>

<form action="${pageContext.request.contextPath}/itineraire/save" method="post">

    <label>Zone :</label>
    <input type="text" name="nomZone" required>

    <br><br>

    <label>Lieu exact :</label>
    <input type="text" name="lieuExact">

    <br><br>

    <label>Heure début :</label>
    <input type="time" name="heureDebutPrevue" required>

    <br><br>

    <label>Heure fin :</label>
    <input type="time" name="heureFinPrevue" required>

    <br><br>

    <label>Jour :</label>
    <select name="jourSemaine" required>
        <option value="">-- Choisir un jour --</option>
        <option value="LUNDI">Lundi</option>
        <option value="MARDI">Mardi</option>
        <option value="MERCREDI">Mercredi</option>
        <option value="JEUDI">Jeudi</option>
        <option value="VENDREDI">Vendredi</option>
        <option value="SAMEDI">Samedi</option>
        <option value="DIMANCHE">Dimanche</option>
    </select>

    <br><br>

    <button type="submit">Enregistrer</button>
</form>

<hr>

<h2>Liste des itinéraires</h2>

<table border="1" cellpadding="8">
    <thead>
        <tr>
            <th>Zone</th>
            <th>Lieu exact</th>
            <th>Heure début</th>
            <th>Heure fin</th>
            <th>Jour</th>
            <th>Action</th>
        </tr>
    </thead>

    <tbody>
        <c:choose>
            <c:when test="${not empty itineraires}">
                <c:forEach items="${itineraires}" var="itineraire">
                    <tr>
                        <td>${itineraire.nomZone}</td>
                        <td>${itineraire.lieuExact}</td>
                        <td>${itineraire.heureDebutPrevue}</td>
                        <td>${itineraire.heureFinPrevue}</td>
                        <td>${itineraire.jourSemaine}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/itineraire/delete?id=${itineraire.id}"
                               onclick="return confirm('Supprimer cet itinéraire ?')">
                                Supprimer
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <tr>
                    <td colspan="6" align="center">Aucun itinéraire enregistré.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>

</table>

</body>
</html>