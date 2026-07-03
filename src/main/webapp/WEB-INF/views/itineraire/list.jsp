<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des itinéraires</title>
</head>
<body>

<h1>Gestion des itinéraires</h1>

<hr>

<a href="${pageContext.request.contextPath}/itineraire/new">
    Ajouter un itinéraire
</a>

<br><br>

<table border="1" cellpadding="8">

    <thead>

    <tr>
        <th>Zone</th>
        <th>Lieu exact</th>
        <th>Heure début</th>
        <th>Heure fin</th>
        <th>Jour</th>
        <th>Actions</th>
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

                        <a href="${pageContext.request.contextPath}/itineraire/${itineraire.idItineraire}/edit">
                            Modifier
                        </a>

                        |

                        <form action="${pageContext.request.contextPath}/itineraire/${itineraire.idItineraire}/delete"
                              method="post"
                              style="display:inline;"
                              onsubmit="return confirm('Supprimer cet itinéraire ?');">

                            <button type="submit">
                                Supprimer
                            </button>

                        </form>

                    </td>

                </tr>

            </c:forEach>

        </c:when>

        <c:otherwise>

            <tr>
                <td colspan="6" align="center">
                    Aucun itinéraire enregistré.
                </td>
            </tr>

        </c:otherwise>

    </c:choose>

    </tbody>

</table>

</body>
</html>