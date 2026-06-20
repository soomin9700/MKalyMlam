<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Itinéraires</title>
</head>
<body>


    <h2>Ajouter un itinéraire</h2>
    <form action="/itineraire/save" method="post">
        <label>Zone :
            <input type="text" name="nomZone" required/>
        </label>
        <br/>
        <label>Heure début :
            <input type="time" name="heureDebut" required/>
        </label>
        <br/>
        <label>Heure fin :
            <input type="time" name="heureFin" required/>
        </label>
        <br/><br/>
        <button type="submit">Ajouter</button>
    </form>

    <hr/>
    <h2>Liste des itinéraires</h2>
    <ul>
        <c:forEach items="${itineraires}" var="itineraire">
            <li>
                <strong>${itineraire.nomZone}</strong>
                ${itineraire.heureDebut} - ${itineraire.heureFin}
                [<a href="/itineraire/delete?id=${itineraire.id}" onclick="return confirm('Supprimer ?')">supprimer</a>]
            </li>
        </c:forEach>
    </ul>
</body>
</html>
