<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ouvrir une session</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">

</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="sessionTruck"/>

    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />
    
    <h1>Ouvrir une session</h1>
    
    <hr>

    <a href="${pageContext.request.contextPath}/session/liste" class="back-link">
        <i class="fas fa-arrow-left"></i>Retour a la liste
    </a>

    <br><br>

    <form action="${pageContext.request.contextPath}/session/ouvrir" method="post">

        <label for="idTruck">Camion :</label>
        <select id="idTruck" name="idTruck" required>
            <option value="">-- Sélectionner un camion --</option>
            <c:forEach items="${trucks}" var="truck">
                <option value="${truck.id}">${truck.immatriculation}</option>
            </c:forEach>
        </select>
        <br><br>

        <label for="idItineraire">Itinéraire :</label>
        <select id="idItineraire" name="idItineraire" required>
            <option value="">-- Sélectionner un itinéraire --</option>
            <c:forEach items="${itineraires}" var="itineraire">
                <option value="${itineraire.id}">${itineraire.nomZone} - ${itineraire.lieuExact}</option>
            </c:forEach>
        </select>
        <br><br>

        <label for="idChauffeur">Chauffeur :</label>
        <select id="idChauffeur" name="idChauffeur" required>
            <option value="">-- Sélectionner un chauffeur --</option>
            <c:forEach items="${chauffeurs}" var="chauffeur">
                <option value="${chauffeur.id}">${chauffeur.prenom} ${chauffeur.nom}</option>
            </c:forEach>
        </select>
        <br><br>

        <label for="fondDeCaisseOuverture">Fond de caisse (€) :</label>
        <input type="number" id="fondDeCaisseOuverture" name="fondDeCaisseOuverture"
            step="0.01" min="0" required>
        <br><br>

        <button type="submit">Démarrer la journée</button>
        <button type="reset">Réinitialiser</button>
        
    </form>

</div>

</body>
</html>
