<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Publier la position du truck</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="localisation"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <!-- Retour -->
        <a href="${pageContext.request.contextPath}/localisation/list"
           class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste des positions
        </a>

        <div class="form-section">

            <!-- Affichage des messages -->
            <c:if test="${not empty success}">
                <div class="alert alert-success" style="padding: 15px; background: #d4edda; border-radius: 8px; color: #155724; margin-bottom: 20px;">
                    <i class="fas fa-check-circle"></i> ${success}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger" style="padding: 15px; background: #f8d7da; border-radius: 8px; color: #721c24; margin-bottom: 20px;">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/localisation/publier" method="post">

                <h1>
                    <i class="fas fa-map-pin" style="color: var(--primary); margin-right: 12px;"></i>
                    Publier la position d'un truck
                </h1>

                <p style="color:#6b7280;margin-bottom:30px;">
                    Sélectionnez un truck en session et définissez sa zone de présence.
                </p>

                <hr style="border:none;border-top:1px solid var(--gray);margin:20px 0;">
                <br>

                <!-- Sélection de la Session Truck -->
                <div class="form-group">
                    <label for="idSession">
                        Truck en session
                        <span class="required-star">*</span>
                    </label>
                    <select id="idSession" name="idSession" required>
                        <option value="">-- Sélectionner un truck en session --</option>
                        <c:forEach items="${sessions}" var="session">
                            <option value="${session.id}">
                                ${session.truck.immatriculation} - 
                                ${session.itineraire.nomZone} 
                                (${session.dateSession})
                            </option>
                        </c:forEach>
                    </select>
                    <small>
                        Sélectionnez le truck dont vous voulez publier la position.
                    </small>
                </div>

                <!-- Sélection de l'Itinéraire (Zone) -->
                <div class="form-group">
                    <label for="idItineraire">
                        Zone / Itinéraire
                        <span class="required-star">*</span>
                    </label>
                    <select id="idItineraire" name="idItineraire" required>
                        <option value="">-- Sélectionner une zone --</option>
                        <c:forEach items="${itineraires}" var="itineraire">
                            <option value="${itineraire.id}">
                                ${itineraire.nomZone} - ${itineraire.lieuExact}
                                (${itineraire.heureDebutPrevue} - ${itineraire.heureFinPrevue})
                            </option>
                        </c:forEach>
                    </select>
                    <small>
                        Sélectionnez la zone où le truck sera positionné.
                    </small>
                </div>

                <!-- Heure d'arrivée (optionnel) -->
                <div class="form-group">
                    <label for="heureArrivee">
                        Heure d'arrivée (optionnel)
                    </label>
                    <input type="time" id="heureArrivee" name="heureArrivee" 
                           value="<%= java.time.LocalTime.now().toString().substring(0, 5) %>">
                    <small>
                        Laissez vide pour utiliser l'heure actuelle.
                    </small>
                </div>

                <!-- Boutons d'action -->
                <div class="form-actions">
                    <button type="submit" class="btn-success">
                        <i class="fas fa-paper-plane"></i>
                        Publier la position
                    </button>

                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>

                    <a href="${pageContext.request.contextPath}/localisation/list"
                       style="margin-left:auto; align-self:center; color:var(--primary); text-decoration:none;">
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