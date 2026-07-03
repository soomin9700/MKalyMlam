<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>
    <c:choose>
        <c:when test="${isEdit}">
            Modifier un itinéraire
        </c:when>
        <c:otherwise>
            Ajouter un itinéraire
        </c:otherwise>
    </c:choose>
</title>
</head>
<body>

<h1>Gestion des itinéraires</h1>
<hr>

<h2>
    <c:choose>
        <c:when test="${isEdit}">
            Modifier un itinéraire
        </c:when>
        <c:otherwise>
            Ajouter un itinéraire
        </c:otherwise>
    </c:choose>
</h2>

<form action="${actionUrl}" method="post">

    <c:if test="${isEdit}">
        <input type="hidden" name="id" value="${itineraire.idItineraire}">
    </c:if>

    <label>Zone :</label>
    <input type="text"
           name="nomZone"
           value="${itineraire.nomZone}"
           required>

    <br><br>

    <label>Lieu exact :</label>
    <input type="text"
           name="lieuExact"
           value="${itineraire.lieuExact}">

    <br><br>

    <label>Heure début :</label>
    <input type="time"
           name="heureDebutPrevue"
           value="${itineraire.heureDebutPrevue}"
           required>

    <br><br>

    <label>Heure fin :</label>
    <input type="time"
           name="heureFinPrevue"
           value="${itineraire.heureFinPrevue}"
           required>

    <br><br>

    <label>Jour :</label>

    <select name="jourSemaine" required>

        <option value="">-- Choisir un jour --</option>

        <option value="LUNDI" ${itineraire.jourSemaine == 'LUNDI' ? 'selected' : ''}>Lundi</option>
        <option value="MARDI" ${itineraire.jourSemaine == 'MARDI' ? 'selected' : ''}>Mardi</option>
        <option value="MERCREDI" ${itineraire.jourSemaine == 'MERCREDI' ? 'selected' : ''}>Mercredi</option>
        <option value="JEUDI" ${itineraire.jourSemaine == 'JEUDI' ? 'selected' : ''}>Jeudi</option>
        <option value="VENDREDI" ${itineraire.jourSemaine == 'VENDREDI' ? 'selected' : ''}>Vendredi</option>
        <option value="SAMEDI" ${itineraire.jourSemaine == 'SAMEDI' ? 'selected' : ''}>Samedi</option>
        <option value="DIMANCHE" ${itineraire.jourSemaine == 'DIMANCHE' ? 'selected' : ''}>Dimanche</option>

    </select>

    <br><br>

    <button type="submit">

        <c:choose>
            <c:when test="${isEdit}">
                Modifier
            </c:when>
            <c:otherwise>
                Enregistrer
            </c:otherwise>
        </c:choose>

    </button>

    <button type="reset">Réinitialiser</button>

    <a href="${pageContext.request.contextPath}/itineraire">
        Annuler
    </a>

</form>

</body>
</html>