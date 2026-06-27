<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sessions du jour</title>
</head>
<body>

<h1>Sessions du jour</h1>

<hr>

<c:if test="${not empty success}">
    <p style="color:green;">${success}</p>
</c:if>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<a href="${pageContext.request.contextPath}/session/ouvrir">Ouvrir une session</a>

<br><br>

<table border="1" cellpadding="8">

    <thead>
    <tr>
        <th>ID</th>
        <th>Camion</th>
        <th>Chauffeur</th>
        <th>Itinéraire</th>
        <th>Statut</th>
        <th>Fond caisse départ</th>
        <th>Actions</th>
    </tr>
    </thead>

    <tbody>

    <c:choose>
        <c:when test="${not empty sessions}">

            <c:forEach items="${sessions}" var="session" varStatus="loop">
                <tr>
                    <td>${session.id}</td>
                    <td>${session.truck.immatriculation}</td>
                    <td>${chauffeurs[loop.index]}</td>
                    <td>${session.itineraire.nomZone}</td>
                    <td>${session.statutSession.libelle}</td>
                    <td>${session.fondDeCaisseOuverture}</td>
                    <td>
                        <c:if test="${session.statutSession.libelle == 'OUVERTE'}">
                            <form action="${pageContext.request.contextPath}/session/cloturer"
                                  method="post" style="display:inline;">
                                <input type="hidden" name="idSession" value="${session.id}">
                                <input type="number" name="fondDeCaisseCloture"
                                       step="0.01" min="0" placeholder="Fond clôture" required>
                                <button type="submit">Clôturer</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>

        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="7" align="center">Aucune session pour aujourd'hui.</td>
            </tr>
        </c:otherwise>
    </c:choose>

    </tbody>

</table>

</body>
</html>
